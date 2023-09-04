package io.connorwyatt.todos.domain.events

import io.connorwyatt.todos.common.domain.events.Event
import io.connorwyatt.todos.common.domain.events.VersionedEventType
import kotlinx.serialization.*

@Serializable
data class TodoCompleted(val todoId: String) : Event {
    companion object {
        val type = VersionedEventType("todos.todoCompleted", 1)
    }
}
