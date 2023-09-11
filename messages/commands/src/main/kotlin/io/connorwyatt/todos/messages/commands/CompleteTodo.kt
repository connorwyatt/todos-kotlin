package io.connorwyatt.todos.messages.commands

import io.connorwyatt.todos.common.messaging.commands.Command
import kotlinx.serialization.*

@Serializable
data class CompleteTodo(val id: String) : Command {
    companion object {
        const val type = "CompleteTodo"
    }
}
