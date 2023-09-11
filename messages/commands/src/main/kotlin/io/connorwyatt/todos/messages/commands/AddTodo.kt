package io.connorwyatt.todos.messages.commands

import io.connorwyatt.todos.common.messaging.commands.Command
import kotlinx.serialization.*

@Serializable
data class AddTodo(val id: String, val title: String) : Command {
    companion object {
        const val type = "AddTodo"
    }
}
