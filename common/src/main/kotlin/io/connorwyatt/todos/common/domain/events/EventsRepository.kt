package io.connorwyatt.todos.common.domain.events

import io.connorwyatt.todos.common.domain.streams.StreamDescriptor

interface EventsRepository {
    suspend fun readStream(streamDescriptor: StreamDescriptor): List<EventEnvelope<out Event>>

    suspend fun appendToStream(
        streamDescriptor: StreamDescriptor.Origin,
        events: List<Event>,
        expectedStreamVersion: Long?,
    )
}
