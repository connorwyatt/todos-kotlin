package io.connorwyatt.todos.common.domain.events

import com.eventstore.dbclient.ResolvedEvent
import java.time.Instant

data class EventMetadata(
    val timestamp: Instant,
    val streamPosition: Long,
    val aggregatedStreamPosition: Long
) {
    companion object {
        fun fromResolvedEvent(resolvedEvent: ResolvedEvent): EventMetadata {
            return EventMetadata(
                resolvedEvent.event.created,
                resolvedEvent.event.revision,
                resolvedEvent.originalEvent.revision
            )
        }
    }
}
