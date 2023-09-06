package io.connorwyatt.todos.restapi.app

import io.connorwyatt.todos.common.domain.eventstore.EventStoreConfiguration

data class Configuration(val eventStore: EventStoreConfiguration)
