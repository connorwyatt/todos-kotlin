package io.connorwyatt.todos.projector

import io.connorwyatt.todos.common.domain.bindEventHandler
import org.kodein.di.*

val todosProjectorDependenciesModule by
    DI.Module {
        bindEventHandler<InMemoryTodosProjector>(setOf("${'$'}ce-todos")) {
            new(::InMemoryTodosProjector)
        }
    }
