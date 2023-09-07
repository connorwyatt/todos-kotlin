package io.connorwyatt.todos.projector

import io.connorwyatt.todos.common.domain.bindEventHandler
import io.connorwyatt.todos.common.domain.streams.StreamDescriptor
import org.kodein.di.*

val todosProjectorDependenciesModule by
    DI.Module {
        bindEventHandler<InMemoryTodosProjector>(setOf(StreamDescriptor.Category("todos"))) {
            new(::InMemoryTodosProjector)
        }
    }
