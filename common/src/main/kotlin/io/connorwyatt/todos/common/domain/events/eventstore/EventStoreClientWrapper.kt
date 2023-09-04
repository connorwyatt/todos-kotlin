package io.connorwyatt.todos.common.domain.events.eventstore

import com.eventstore.dbclient.*
import kotlinx.coroutines.future.await

class EventStoreClientWrapper(private val eventStoreDBClient: EventStoreDBClient) {
    suspend fun readStream(
        streamName: String,
        readStreamOptions: ReadStreamOptions,
    ): ReadResult {
        val readResult =
            try {
                eventStoreDBClient.readStream(streamName, readStreamOptions).await()
            } catch (exception: Exception) {
                return ReadResult.Failure(exception)
            }

        return ReadResult.Success(readResult.events, readResult.lastStreamPosition)
    }

    suspend fun appendToStream(
        streamName: String,
        options: AppendToStreamOptions,
        events: List<EventData>,
    ): WriteResult {
        val writeResult =
            try {
                eventStoreDBClient
                    .appendToStream(streamName, options, *events.toTypedArray())
                    .await()
            } catch (exception: Exception) {
                return WriteResult.Failure(exception)
            }

        return WriteResult.Success(writeResult.logPosition.commitUnsigned)
    }

    sealed interface ReadResult {
        data class Success(val events: List<ResolvedEvent>, val streamPosition: Long) : ReadResult

        data class Failure(val exception: Exception) : ReadResult
    }

    sealed interface WriteResult {
        data class Success(val streamPosition: Long) : WriteResult

        data class Failure(val exception: Exception) : WriteResult
    }
}
