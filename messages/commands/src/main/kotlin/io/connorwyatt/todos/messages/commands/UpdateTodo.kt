package io.connorwyatt.todos.messages.commands

import io.connorwyatt.common.optional.Optional
import io.connorwyatt.common.optional.Optional.Absent
import io.connorwyatt.common.rabbitmq.Command
import kotlinx.serialization.Serializable

@Serializable
data class UpdateTodo(val id: String, val title: Optional<String> = Absent) : Command {
    companion object {
        const val type = "UpdateTodo"
    }
}
