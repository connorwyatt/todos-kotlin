package io.connorwyatt.todos.domain.events

import io.connorwyatt.common.eventstore.events.Event
import io.connorwyatt.common.eventstore.events.VersionedEventType
import kotlinx.serialization.Serializable

@Serializable
data class TodoCompleted(val todoId: String) : Event {
    companion object {
        val type = VersionedEventType("todos.todoCompleted", 1)
    }
}
