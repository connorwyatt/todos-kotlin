package io.connorwyatt.todos.common.domain.inmemory

import io.connorwyatt.todos.common.domain.eventhandlers.EventHandler
import io.connorwyatt.todos.common.domain.eventhandlers.EventHandlerMap
import io.connorwyatt.todos.common.domain.events.*
import io.connorwyatt.todos.common.domain.streams.StreamDescriptor
import io.connorwyatt.todos.common.time.clock.Clock
import java.time.Duration
import java.util.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.time.withTimeout

class InMemoryEventsRepository(
    private val clock: Clock,
    private val eventMap: EventMap,
    private val eventHandlerMap: EventHandlerMap,
    private val eventHandlers: Set<EventHandler>,
) : EventsRepository {
    private var streams = emptyMap<StreamDescriptor, List<EventEnvelope<out Event>>>()
    private val eventsToPropagate = LinkedList<Pair<StreamDescriptor, EventEnvelope<out Event>>>()

    override suspend fun readStream(
        streamDescriptor: StreamDescriptor
    ): List<EventEnvelope<out Event>> = (streams[streamDescriptor] ?: emptyList())

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

            updateStreams(streamDescriptor, events)

            return
        }

        if (stream == null) {
            // TODO: Check if throwing here is right.
            throw Exception()
        }

        updateStreams(streamDescriptor, events)
    }

    suspend fun startEventPropagation() {
        while (true) {
            if (eventsToPropagate.isNotEmpty()) {
                val (streamDescriptor, envelope) = eventsToPropagate.pollFirst()

                propagateEventToHandlers(streamDescriptor, envelope)
            }

            delay(20)
        }
    }

    suspend fun waitForEmptyEventPropagationQueue(timeout: Duration) {
        withTimeout(timeout) {
            while (eventsToPropagate.isNotEmpty()) {
                delay(50)
            }
        }
    }

    @Synchronized
    private fun updateStreams(
        originStreamDescriptor: StreamDescriptor.Origin,
        events: List<Event>
    ) {
        val now = clock.now()

        val envelopes =
            events.flatMap { event ->
                val versionedEventType = eventMap.versionedEventType(event)

                val nextStreamPositionForOriginStream = nextStreamPosition(originStreamDescriptor)

                val categoryStreamDescriptor =
                    StreamDescriptor.Category(originStreamDescriptor.category)
                val eventTypeStreamDescriptor = StreamDescriptor.EventType(versionedEventType)
                val allStreamDescriptor = StreamDescriptor.All

                listOf(
                        originStreamDescriptor,
                        categoryStreamDescriptor,
                        eventTypeStreamDescriptor,
                        allStreamDescriptor
                    )
                    .map {
                        val envelope =
                            EventEnvelope(
                                event,
                                EventMetadata(
                                    now,
                                    nextStreamPositionForOriginStream,
                                    nextStreamPosition(it)
                                )
                            )

                        updateStream(it, envelope)

                        it to envelope
                    }
            }

        enqueueEventsForPropagation(envelopes)
    }

    private fun updateStream(
        streamDescriptor: StreamDescriptor,
        envelope: EventEnvelope<out Event>
    ) {
        val stream = streams[streamDescriptor]

        streams =
            if (stream == null) {
                streams.plus(streamDescriptor to listOf(envelope))
            } else {
                streams.mapValues {
                    if (it.key != streamDescriptor) it.value else it.value.plus(envelope)
                }
            }
    }

    private fun nextStreamPosition(streamDescriptor: StreamDescriptor): Long {
        val lastStreamPositionInOriginStream =
            streams[streamDescriptor]?.lastOrNull()?.metadata?.streamPosition ?: -1
        return lastStreamPositionInOriginStream + 1
    }

    private fun enqueueEventsForPropagation(
        envelopes: List<Pair<StreamDescriptor, EventEnvelope<Event>>>
    ) {
        eventsToPropagate.addAll(envelopes)
    }

    private suspend fun propagateEventToHandlers(
        streamDescriptor: StreamDescriptor,
        envelope: EventEnvelope<out Event>,
    ) {
        eventHandlerMap
            .eventHandlersFor(streamDescriptor)
            .map { eventHandlerClazz -> eventHandlers.single { it::class == eventHandlerClazz } }
            .forEach { it.handleEvent(streamDescriptor, envelope.event, envelope.metadata) }
    }
}
