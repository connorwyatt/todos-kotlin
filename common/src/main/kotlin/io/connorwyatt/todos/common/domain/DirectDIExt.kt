package io.connorwyatt.todos.common.domain

import io.connorwyatt.todos.common.domain.aggregates.Aggregate
import io.connorwyatt.todos.common.domain.aggregates.AggregateMap
import io.connorwyatt.todos.common.domain.events.Event
import io.connorwyatt.todos.common.domain.events.EventMap
import io.connorwyatt.todos.common.domain.events.VersionedEventType
import org.kodein.di.*

inline fun <reified TAggregate : Aggregate> DirectDI.registerAggregate(category: String) {
    instance<AggregateMap>().registerAggregate<TAggregate>(category)
}

inline fun <reified TEvent : Event> DirectDI.registerEvent(versionedEventType: VersionedEventType) {
    instance<EventMap>().registerEvent<TEvent>(versionedEventType)
}
