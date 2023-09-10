package io.connorwyatt.todos.common.messaging

data class RabbitMQConfiguration(
    val connectionString: String,
    val exchangeName: String,
)
