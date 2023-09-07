package io.connorwyatt.todos.common.domain.inmemory

import io.connorwyatt.todos.common.domain.events.Event
import io.connorwyatt.todos.common.domain.events.EventEnvelope
import io.connorwyatt.todos.common.domain.events.EventMetadata
import io.connorwyatt.todos.common.domain.events.EventsRepository
import io.connorwyatt.todos.common.domain.streams.StreamDescriptor
import io.connorwyatt.todos.common.time.clock.Clock
import java.time.Instant

class InMemoryEventsRepository(private val clock: Clock) : EventsRepository {
    private var streams = emptyMap<StreamDescriptor, List<EventWithMetadata>>()

    override suspend fun readStream(
        streamDescriptor: StreamDescriptor
    ): List<EventEnvelope<out Event>> {
        return (streams[streamDescriptor] ?: emptyList()).map { (event, timestamp, streamPosition)
            ->
            EventEnvelope(event, EventMetadata(timestamp, streamPosition, streamPosition))
        }
    }

    override suspend fun appendToStream(
        streamDescriptor: StreamDescriptor.Origin,
        events: List<Event>,
        expectedStreamVersion: Long?,
    ) {
        val stream = streams[streamDescriptor]

        if (expectedStreamVersion == null) {
            if (!stream.isNullOrEmpty()) {
                // TODO: Check if throwing here is right.
                throw Exception()
            }

            val now = clock.now()
            streams = streams.plus(streamDescriptor to wrapWithMetadata(events, now))

            return
        }

        if (stream == null) {
            // TODO: Check if throwing here is right.
            throw Exception()
        }

        val now = clock.now()
        val lastStreamPosition = stream.last().streamPosition

        streams =
            streams.mapValues {
                if (it.key == streamDescriptor)
                    it.value.plus(wrapWithMetadata(events, now, lastStreamPosition))
                else it.value
            }
    }

    private fun wrapWithMetadata(
        events: List<Event>,
        timestamp: Instant,
        lastStreamPosition: Long = -1
    ): List<EventWithMetadata> {
        val nextStreamPosition = lastStreamPosition + 1

        return events.mapIndexed { index, event ->
            val streamPosition = nextStreamPosition + index
            EventWithMetadata(event, timestamp, streamPosition)
        }
    }

    private data class EventWithMetadata(
        val event: Event,
        val timestamp: Instant,
        val streamPosition: Long
    )
}
