package io.connorwyatt.todos.common.domain.events.eventstore

import com.eventstore.dbclient.AppendToStreamOptions
import com.eventstore.dbclient.EventDataBuilder
import com.eventstore.dbclient.ExpectedRevision
import com.eventstore.dbclient.ReadStreamOptions
import io.connorwyatt.todos.common.domain.events.*

class EventStoreEventsRepository(
    private val eventStoreClient: EventStoreClientWrapper,
    private val eventMap: EventMap,
    private val resolvedEventMapper: ResolvedEventMapper
) : EventsRepository {
    override suspend fun readStream(streamName: String): List<EventEnvelope<out Event>> {
        val result = eventStoreClient.readStream(streamName, readStreamOptions)

        return when (result) {
            is EventStoreClientWrapper.ReadResult.Failure -> emptyList()
            is EventStoreClientWrapper.ReadResult.Success ->
                result.events.map(resolvedEventMapper::map)
        }
    }

    override suspend fun appendToStream(
        streamId: String,
        events: List<Event>,
        expectedStreamVersion: Long?,
    ) {
        if (events.isEmpty()) {
            return
        }

        val eventDataList =
            events.map {
                val versionedEventType = eventMap.versionedEventType(it)
                val serializedEvent =
                    eventJson.encodeToString(EventTransformingSerializer, it).toByteArray()
                EventDataBuilder.json(versionedEventType.toString(), serializedEvent).build()
            }

        val options =
            expectedStreamVersion?.let { appendToStreamOptions(it) }
                ?: appendToStreamOptionsNoStream

        val result = eventStoreClient.appendToStream(streamId, options, eventDataList)

        if (result is EventStoreClientWrapper.WriteResult.Failure) throw result.exception
    }

    companion object {
        private val readStreamOptions by lazy { ReadStreamOptions.get().apply { fromStart() } }

        private val appendToStreamOptionsNoStream by lazy {
            AppendToStreamOptions.get().expectedRevision(ExpectedRevision.noStream())
        }

        private fun appendToStreamOptions(revision: Long): AppendToStreamOptions {
            return AppendToStreamOptions.get().expectedRevision(revision)
        }
    }
}
