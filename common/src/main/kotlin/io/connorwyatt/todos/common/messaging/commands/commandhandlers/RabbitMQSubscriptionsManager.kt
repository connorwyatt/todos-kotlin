package io.connorwyatt.todos.common.messaging.commands.commandhandlers

import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.Connection
import com.rabbitmq.client.DeliverCallback
import com.rabbitmq.client.Delivery
import io.connorwyatt.todos.common.messaging.commands.Command
import io.connorwyatt.todos.common.messaging.commands.CommandMap
import io.connorwyatt.todos.common.messaging.commands.queues.CommandQueueList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.*
import kotlinx.serialization.json.*

internal class RabbitMQSubscriptionsManager(
    private val exchangeName: String,
    private val connection: Connection,
    private val commandQueueList: CommandQueueList,
    private val commandMap: CommandMap,
    private val commandHandlerRouter: CommandHandlerRouter
) {
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    internal fun start() {
        subscribeToQueues()
    }

    private fun subscribeToQueues() {
        commandQueueList.definitions.forEach { definition ->
            coroutineScope.launch {
                val channel = connection.createChannel()

                channel.basicConsume(
                    "$exchangeName.${definition.name}",
                    false,
                    DeliverCallback { _, message ->
                        runBlocking {
                            try {
                                commandHandlerRouter.handle(deserializeCommand(message))

                                channel.basicAck(message.envelope.deliveryTag, false)
                            } catch (_: Exception) {
                                channel.basicNack(message.envelope.deliveryTag, false, false)
                            }
                        }
                    },
                    CancelCallback {}
                )
            }
        }
    }

    private fun deserializeCommand(message: Delivery): Command {
        @OptIn(InternalSerializationApi::class)
        val serializer = commandMap.classFor(message.properties.type).serializer()
        return Json.decodeFromString(serializer, message.body.decodeToString())
    }
}
