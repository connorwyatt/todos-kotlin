package io.connorwyatt.todos.common.domain.events

interface EventsRepository {
    suspend fun readStream(streamName: String): List<EventEnvelope<out Event>>

    suspend fun appendToStream(
        streamId: String,
        events: List<Event>,
        expectedStreamVersion: Long?,
    )
}
