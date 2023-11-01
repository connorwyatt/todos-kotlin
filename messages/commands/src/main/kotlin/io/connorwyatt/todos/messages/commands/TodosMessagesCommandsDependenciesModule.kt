package io.connorwyatt.todos.messages.commands

import io.connorwyatt.common.rabbitmq.kodein.bindCommandDefinition
import org.kodein.di.*

val todosMessagesCommandsDependenciesModule by
    DI.Module {
        bindCommandDefinition<AddTodo>(AddTodo.type)
        bindCommandDefinition<UpdateTodo>(UpdateTodo.type)
        bindCommandDefinition<CompleteTodo>(CompleteTodo.type)
    }
