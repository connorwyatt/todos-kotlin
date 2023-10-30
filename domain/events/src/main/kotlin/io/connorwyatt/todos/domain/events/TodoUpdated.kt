package io.connorwyatt.todos.domain.events

import io.connorwyatt.common.eventstore.events.Event
import io.connorwyatt.common.eventstore.events.VersionedEventType
import io.connorwyatt.todos.common.models.Optional
import io.connorwyatt.todos.common.models.Optional.Absent
import kotlinx.serialization.Serializable

@Serializable
data class TodoUpdated(val todoId: String, val title: Optional<String> = Absent) : Event {
    companion object {
        val type = VersionedEventType("todos.todoUpdated", 1)
    }
}
