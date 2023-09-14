package io.connorwyatt.todos.domain.events

import io.connorwyatt.todos.common.domain.events.Event
import io.connorwyatt.todos.common.domain.events.VersionedEventType
import io.connorwyatt.todos.common.models.Optional
import io.connorwyatt.todos.common.models.Optional.Absent
import kotlinx.serialization.*

@Serializable
data class TodoUpdated(val todoId: String, val title: Optional<String> = Absent) : Event {
    companion object {
        val type = VersionedEventType("todos.todoUpdated", 1)
    }
}
