package io.connorwyatt.todos.restapi.app

import io.connorwyatt.todos.common.data.mongodb.MongoDBConfiguration
import io.connorwyatt.todos.common.domain.eventstore.EventStoreConfiguration
import io.connorwyatt.todos.common.messaging.RabbitMQConfiguration
import io.connorwyatt.todos.data.DataConfiguration

data class Configuration(
    val data: DataConfiguration,
    val eventStore: EventStoreConfiguration,
    val mongoDB: MongoDBConfiguration,
    val rabbitMQ: RabbitMQConfiguration,
)
