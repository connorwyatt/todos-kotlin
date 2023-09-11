package io.connorwyatt.todos.common.messaging.commands.bus

import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Connection
import io.connorwyatt.todos.common.messaging.commands.Command
import io.connorwyatt.todos.common.messaging.commands.CommandEnvelope
import io.connorwyatt.todos.common.messaging.commands.CommandMap
import io.connorwyatt.todos.common.messaging.commands.routing.CommandRoutingRules
import io.connorwyatt.todos.common.messaging.commands.routing.RoutingKeyUtilities
import kotlin.reflect.KClass
import kotlinx.serialization.*
import kotlinx.serialization.json.*

class RabbitMQCommandBus
internal constructor(
    private val connection: Connection,
    private val exchangeName: String,
    private val commandMap: CommandMap,
    private val commandRoutingRules: CommandRoutingRules
) : CommandBus {
    override suspend fun send(commandEnvelope: CommandEnvelope) {
        send(listOf(commandEnvelope))
    }

    override suspend fun send(commandEnvelopes: List<CommandEnvelope>) {
        connection.createChannel().use { channel ->
            try {
                channel.txSelect()
                commandEnvelopes.forEach { commandEnvelope ->
                    // TODO: Fix this.
                    val commandClass = commandEnvelope.command::class as KClass<Command>

                    @OptIn(InternalSerializationApi::class)
                    val serializer = commandClass.serializer()

                    val serializedCommand =
                        Json.encodeToString(serializer, commandEnvelope.command).encodeToByteArray()

                    val destinationQueueName = commandRoutingRules.queueFor(commandClass)

                    channel.basicPublish(
                        exchangeName,
                        RoutingKeyUtilities.routingKeyFor("$exchangeName.$destinationQueueName"),
                        AMQP.BasicProperties.Builder()
                            .apply { type(commandMap.typeFor(commandClass)) }
                            .build(),
                        serializedCommand
                    )
                }
                channel.txCommit()
            } catch (exception: Exception) {
                channel.txRollback()
            }
        }
    }
}
