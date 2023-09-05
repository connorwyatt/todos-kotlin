package io.connorwyatt.todos.common.domain

import com.eventstore.dbclient.EventStoreDBClient
import com.eventstore.dbclient.EventStoreDBConnectionString
import io.connorwyatt.todos.common.domain.aggregates.Aggregate
import io.connorwyatt.todos.common.domain.aggregates.AggregateMap
import io.connorwyatt.todos.common.domain.aggregates.AggregateMapDefinition
import io.connorwyatt.todos.common.domain.aggregates.AggregatesRepository
import io.connorwyatt.todos.common.domain.eventhandlers.EventHandler
import io.connorwyatt.todos.common.domain.eventhandlers.EventHandlerDefinition
import io.connorwyatt.todos.common.domain.eventhandlers.EventHandlerMap
import io.connorwyatt.todos.common.domain.events.EventMap
import io.connorwyatt.todos.common.domain.events.EventMapDefinition
import io.connorwyatt.todos.common.domain.events.EventsRepository
import io.connorwyatt.todos.common.domain.events.ResolvedEventMapper
import io.connorwyatt.todos.common.domain.events.eventstore.EventStoreClientWrapper
import io.connorwyatt.todos.common.domain.events.eventstore.EventStoreEventsRepository
import org.kodein.di.*

val domainDependenciesModule by
    DI.Module {
        bindSingletonOf(::AggregatesRepository)
        bindSingletonOf(::AggregateMap)
        bindSet<AggregateMapDefinition<Aggregate>>()

        val settings =
            EventStoreDBConnectionString.parseOrThrow(
                // TODO: Move this to config.
                "esdb://admin:changeit@localhost:2113?tls=false"
            )

        bindProvider<EventsRepository> { new(::EventStoreEventsRepository) }
        bindSingleton<EventStoreDBClient> { EventStoreDBClient.create(settings) }
        bindProviderOf(::EventStoreClientWrapper)
        bindSingletonOf(::EventMap)
        bindSet<EventMapDefinition>()
        bindProviderOf(::ResolvedEventMapper)

        bindSet<EventHandler>()
        bindSingletonOf(::EventHandlerMap)
        bindSet<EventHandlerDefinition>()
    }
