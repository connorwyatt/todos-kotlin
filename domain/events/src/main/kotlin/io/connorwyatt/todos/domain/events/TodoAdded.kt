package io.connorwyatt.todos.domain.events

import io.connorwyatt.todos.common.domain.events.Event
import io.connorwyatt.todos.common.domain.events.VersionedEventType
import kotlinx.serialization.*

@Serializable
data class TodoAdded(val todoId: String, val title: String) : Event {
    companion object {
        val type = VersionedEventType("todos.todoAdded", 1)
    }
}
