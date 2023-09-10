package io.connorwyatt.todos.common.messaging.commands.queues

import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import io.connorwyatt.todos.common.messaging.commands.routing.RoutingKeyUtilities
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class RabbitMQCommandQueueCreator(
    private val exchangeName: String,
    private val connection: Connection,
    private val commandQueueList: CommandQueueList
) : CommandQueueCreator {
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    override suspend fun createQueues() {
        connection.createChannel().use { createQueues(it) }
    }

    private suspend fun createQueues(channel: Channel) {
        createExchange(channel, exchangeName)

        commandQueueList.definitions
            .map { queue ->
                coroutineScope.launch { createQueue(channel, exchangeName, queue.name) }
            }
            .joinAll()
    }

    private suspend fun createExchange(channel: Channel, exchangeName: String) {
        channel.exchangeDeclare(exchangeName, "direct", true)
    }

    private suspend fun createQueue(channel: Channel, exchangeName: String, queueName: String) {
        val fullQueueName = "$exchangeName.${queueName}"
        val deadLetterQueueName = "${fullQueueName}.dlq"

        val queueRoutingKey = RoutingKeyUtilities.routingKeyFor(fullQueueName)
        val deadLetterQueueRoutingKey = RoutingKeyUtilities.routingKeyFor(deadLetterQueueName)

        channel.queueDeclare(deadLetterQueueName, true, false, false, null)
        channel.queueBind(deadLetterQueueName, exchangeName, deadLetterQueueRoutingKey)
        val args =
            mapOf(
                "x-dead-letter-exchange" to exchangeName,
                "x-dead-letter-routing-key" to deadLetterQueueRoutingKey
            )
        channel.queueDeclare(fullQueueName, true, false, false, args)
        channel.queueBind(fullQueueName, exchangeName, queueRoutingKey)
    }
}
