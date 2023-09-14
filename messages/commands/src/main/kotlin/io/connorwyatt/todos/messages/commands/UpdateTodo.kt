package io.connorwyatt.todos.messages.commands

import io.connorwyatt.todos.common.messaging.commands.Command
import io.connorwyatt.todos.common.models.Optional
import io.connorwyatt.todos.common.models.Optional.Absent
import kotlinx.serialization.*

@Serializable
data class UpdateTodo(val id: String, val title: Optional<String> = Absent) : Command {
    companion object {
        const val type = "UpdateTodo"
    }
}
