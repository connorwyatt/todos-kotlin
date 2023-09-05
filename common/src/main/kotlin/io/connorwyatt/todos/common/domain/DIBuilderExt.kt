package io.connorwyatt.todos.common.domain

import io.connorwyatt.todos.common.domain.aggregates.Aggregate
import io.connorwyatt.todos.common.domain.aggregates.AggregateMapDefinition
import io.connorwyatt.todos.common.domain.eventhandlers.EventHandler
import io.connorwyatt.todos.common.domain.eventhandlers.EventHandlerDefinition
import io.connorwyatt.todos.common.domain.events.Event
import io.connorwyatt.todos.common.domain.events.EventMapDefinition
import io.connorwyatt.todos.common.domain.events.VersionedEventType
import org.kodein.di.*
import org.kodein.di.bindings.*

inline fun <reified TAggregate : Aggregate> DI.Builder.bindAggregateDefinition(
    category: String,
    noinline constructor: (String) -> TAggregate
) {
    inBindSet<AggregateMapDefinition<Aggregate>> {
        add { singleton { AggregateMapDefinition(category, TAggregate::class, constructor) } }
    }
}

inline fun <reified TEvent : Event> DI.Builder.bindEventDefinition(
    versionedEventType: VersionedEventType
) {
    inBindSet<EventMapDefinition> {
        add { singleton { EventMapDefinition(versionedEventType, TEvent::class) } }
    }
}

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
