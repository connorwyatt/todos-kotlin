package io.connorwyatt.todos.projector

import io.connorwyatt.common.eventstore.bindEventHandler
import io.connorwyatt.common.eventstore.streams.StreamDescriptor
import org.kodein.di.*

val todosProjectorDependenciesModule by
    DI.Module {
        bindEventHandler<TodosProjector>(setOf(StreamDescriptor.Category("todos"))) {
            new(::TodosProjector)
        }
    }
