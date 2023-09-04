package io.connorwyatt.todos.common.domain.events

import com.eventstore.dbclient.AppendToStreamOptions
import com.eventstore.dbclient.EventDataBuilder
import com.eventstore.dbclient.ExpectedRevision
import com.eventstore.dbclient.ReadStreamOptions
import io.connorwyatt.todos.common.domain.events.eventstore.EventStoreClientWrapper
import kotlinx.serialization.*

@OptIn(InternalSerializationApi::class)
class EventsRepository(
    private val eventStoreClient: EventStoreClientWrapper,
    private val eventMap: EventMap,
) {
    suspend fun readStream(streamName: String): List<EventEnvelope<Event>> {
        val result = eventStoreClient.readStream(streamName, readStreamOptions)

        return when (result) {
            is EventStoreClientWrapper.ReadResult.Failure -> emptyList()
            is EventStoreClientWrapper.ReadResult.Success ->
                result.events.map {
                    val type = VersionedEventType.parse(it.event.eventType)
                    val eventClass = eventMap.eventClass(type)
                    val event =
                        eventJson.decodeFromString(
                            eventClass.serializer(),
                            it.event.eventData.decodeToString()
                        )

                    EventEnvelope(event, EventMetadata.fromResolvedEvent(it))
                }
        }
    }

    suspend fun appendToStream(
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
