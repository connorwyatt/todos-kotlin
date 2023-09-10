package io.connorwyatt.todos.common.messaging

data class RabbitMQConfiguration(
    val useInMemoryRabbitMQ: Boolean,
    val connectionString: String?,
    val exchangeName: String?,
)
