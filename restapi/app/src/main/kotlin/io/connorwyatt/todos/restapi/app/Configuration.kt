package io.connorwyatt.todos.restapi.app

import io.connorwyatt.common.eventstore.configuration.EventStoreConfiguration
import io.connorwyatt.common.mongodb.configuration.MongoDBConfiguration
import io.connorwyatt.common.rabbitmq.configuration.RabbitMQConfiguration
import io.connorwyatt.todos.data.configuration.DataConfiguration

data class Configuration(
    val data: DataConfiguration,
    val eventStore: EventStoreConfiguration,
    val mongoDB: MongoDBConfiguration,
    val rabbitMQ: RabbitMQConfiguration,
)
