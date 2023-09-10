package io.connorwyatt.todos.common.messaging.commands.commandhandlers

import com.rabbitmq.client.*
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
    private var channel: Channel? = null

    internal fun start() {
        coroutineScope.launch {
            channel = connection.createChannel().also { subscribeToQueues(it) }
        }
    }

    private fun subscribeToQueues(channel: Channel) {
        commandQueueList.definitions.map { queue ->
            coroutineScope.launch {
                channel.basicConsume(
                    "$exchangeName.${queue.name}",
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
