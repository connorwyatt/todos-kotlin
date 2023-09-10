package io.connorwyatt.todos.common.messaging.commands.commandhandlers

import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.DeliverCallback
import io.connorwyatt.todos.common.messaging.commands.queues.CommandQueueList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RabbitMQSubscriptionsManager(
    private val exchangeName: String,
    private val connection: Connection,
    private val commandQueueList: CommandQueueList
) {
    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private var channel: Channel? = null

    fun start() {
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
                        channel.basicAck(message.envelope.deliveryTag, false)
                    },
                    CancelCallback {}
                )
            }
        }
    }
}
