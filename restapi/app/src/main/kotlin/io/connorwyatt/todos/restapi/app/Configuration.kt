package io.connorwyatt.todos.restapi.app

import io.connorwyatt.todos.common.domain.eventstore.EventStoreConfiguration
import io.connorwyatt.todos.common.messaging.RabbitMQConfiguration

data class Configuration(
    val eventStore: EventStoreConfiguration,
    val rabbitMQ: RabbitMQConfiguration,
)
