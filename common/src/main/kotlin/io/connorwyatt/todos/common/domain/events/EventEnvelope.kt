package io.connorwyatt.todos.common.domain.events

data class EventEnvelope<TEvent : Event>(val event: TEvent, val metadata: EventMetadata)
