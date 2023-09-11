package io.connorwyatt.todos.messages.commands

import io.connorwyatt.todos.common.messaging.bindCommandDefinition
import org.kodein.di.*

val todosMessagesCommandsDependenciesModule by
    DI.Module {
        bindCommandDefinition<AddTodo>(AddTodo.type)
        bindCommandDefinition<CompleteTodo>(CompleteTodo.type)
    }
