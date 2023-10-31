package io.connorwyatt.todos.restapi.app

import io.connorwyatt.common.eventstore.EventStoreConfiguration
import io.connorwyatt.common.rabbitmq.RabbitMQConfiguration
import io.connorwyatt.todos.common.data.configuration.DataConfiguration
import io.connorwyatt.todos.common.data.mongodb.MongoDBConfiguration

data class Configuration(
    val data: DataConfiguration,
    val eventStore: EventStoreConfiguration,
    val mongoDB: MongoDBConfiguration,
    val rabbitMQ: RabbitMQConfiguration,
)
