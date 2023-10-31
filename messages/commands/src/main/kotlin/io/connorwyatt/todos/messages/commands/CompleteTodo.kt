package io.connorwyatt.todos.messages.commands

import io.connorwyatt.common.rabbitmq.Command
import kotlinx.serialization.Serializable

@Serializable
data class CompleteTodo(val id: String) : Command {
    companion object {
        const val type = "CompleteTodo"
    }
}
