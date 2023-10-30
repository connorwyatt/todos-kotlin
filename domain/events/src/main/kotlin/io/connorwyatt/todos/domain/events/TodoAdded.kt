package io.connorwyatt.todos.domain.events

import io.connorwyatt.common.eventstore.events.Event
import io.connorwyatt.common.eventstore.events.VersionedEventType
import kotlinx.serialization.Serializable

@Serializable
data class TodoAdded(val todoId: String, val title: String) : Event {
    companion object {
        val type = VersionedEventType("todos.todoAdded", 1)
    }
}
