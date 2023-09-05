package io.connorwyatt.todos.domain

import io.connorwyatt.todos.common.domain.registerAggregate
import io.connorwyatt.todos.common.domain.registerEvent
import io.connorwyatt.todos.domain.events.TodoAdded
import io.connorwyatt.todos.domain.events.TodoCompleted
import org.kodein.di.*

val todosDomainDependenciesModule by
    DI.Module {
        onReady {
            registerAggregate<Todo>(Todo.category, ::Todo)

            registerEvent<TodoAdded>(TodoAdded.type)
            registerEvent<TodoCompleted>(TodoCompleted.type)
        }
    }
