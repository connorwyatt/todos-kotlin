package io.connorwyatt.todos.domain

import io.connorwyatt.todos.common.domain.bindAggregateDefinition
import io.connorwyatt.todos.common.domain.bindEventDefinition
import io.connorwyatt.todos.domain.events.TodoAdded
import io.connorwyatt.todos.domain.events.TodoCompleted
import io.connorwyatt.todos.domain.events.TodoUpdated
import org.kodein.di.*

val todosDomainDependenciesModule by
    DI.Module {
        bindAggregateDefinition<Todo>(Todo.category, ::Todo)

        bindEventDefinition<TodoAdded>(TodoAdded.type)
        bindEventDefinition<TodoUpdated>(TodoUpdated.type)
        bindEventDefinition<TodoCompleted>(TodoCompleted.type)
    }
