package io.connorwyatt.todos.common.domain

import io.connorwyatt.todos.common.domain.eventhandlers.EventHandler
import io.connorwyatt.todos.common.domain.eventhandlers.EventHandlerDefinition
import org.kodein.di.*
import org.kodein.di.bindings.*

inline fun <reified TEventHandler : EventHandler> DI.Builder.bindEventHandler(
    streamNames: Set<String>,
    noinline constructor: NoArgBindingDI<*>.() -> TEventHandler,
) {
    inBindSet<EventHandler> { add { singleton { constructor() } } }
    inBindSet<EventHandlerDefinition> {
        streamNames.forEach { streamName ->
            add { singleton { EventHandlerDefinition(streamName, TEventHandler::class) } }
        }
    }
}
