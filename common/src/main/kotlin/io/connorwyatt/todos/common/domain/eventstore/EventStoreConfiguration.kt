package io.connorwyatt.todos.common.domain.eventstore

data class EventStoreConfiguration(
    val connectionString: String?,
    val useInMemoryEventStore: Boolean,
)
