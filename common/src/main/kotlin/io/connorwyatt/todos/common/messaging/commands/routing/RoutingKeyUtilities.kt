package io.connorwyatt.todos.common.messaging.commands.routing

object RoutingKeyUtilities {
    fun routingKeyFor(queueName: String) = "$queueName.routingKey"
}
