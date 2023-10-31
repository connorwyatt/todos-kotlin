package io.connorwyatt.todos.messages.commands

import io.connorwyatt.common.rabbitmq.Command
import kotlinx.serialization.Serializable

@Serializable
data class AddTodo(val id: String, val title: String) : Command {
    companion object {
        const val type = "AddTodo"
    }
}
