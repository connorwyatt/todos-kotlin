package io.connorwyatt.todos.common.domain

import io.connorwyatt.todos.common.domain.aggregates.Aggregate
import io.connorwyatt.todos.common.domain.aggregates.AggregateMap
import io.connorwyatt.todos.common.domain.eventhandlers.EventHandler
import io.connorwyatt.todos.common.domain.eventhandlers.EventHandlerMap
import io.connorwyatt.todos.common.domain.events.Event
import io.connorwyatt.todos.common.domain.events.EventMap
import io.connorwyatt.todos.common.domain.events.VersionedEventType
import org.kodein.di.*

inline fun <reified TAggregate : Aggregate> DirectDI.registerAggregate(
    category: String,
    noinline constructor: (String) -> TAggregate
) {
    instance<AggregateMap>().registerAggregate<TAggregate>(category, constructor)
}

inline fun <reified TEvent : Event> DirectDI.registerEvent(versionedEventType: VersionedEventType) {
    instance<EventMap>().registerEvent<TEvent>(versionedEventType)
}

inline fun <reified TEventHandler : EventHandler> DirectDI.registerEventHandler(
    streamName: String
) {
    instance<EventHandlerMap>().registerEventHandler<TEventHandler>(streamName)
}
