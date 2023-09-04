package io.connorwyatt.todos.domain

import io.connorwyatt.todos.common.domain.events.EventMap
import io.connorwyatt.todos.domain.events.TodoAdded
import io.connorwyatt.todos.domain.events.TodoCompleted
import org.kodein.di.*

val todosDomainDependenciesModule by
    DI.Module {
        onReady {
            instance<EventMap>()
                .registerEventMappings(
                    mapOf(
                        TodoAdded.type to TodoAdded::class,
                        TodoCompleted.type to TodoCompleted::class
                    )
                )
        }
    }
