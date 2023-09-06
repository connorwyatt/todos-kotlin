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
import io.connorwyatt.todos.common.domain.eventstore.EventStoreClientWrapper
import io.connorwyatt.todos.common.domain.eventstore.EventStoreConfiguration
import io.connorwyatt.todos.common.domain.eventstore.EventStoreEventsRepository
import org.kodein.di.*

fun domainDependenciesModule(eventStoreConfiguration: EventStoreConfiguration): DI.Module =
    DI.Module(name = ::domainDependenciesModule.name) {
        bindSingletonOf(::AggregatesRepository)
        bindSingletonOf(::AggregateMap)
        bindSet<AggregateMapDefinition<Aggregate>>()

        bindProvider<EventsRepository> { new(::EventStoreEventsRepository) }
        bindSingleton<EventStoreDBClient> {
            val settings =
                EventStoreDBConnectionString.parseOrThrow(
                    eventStoreConfiguration.connectionString
                        ?: throw Exception("EventStore connectionString is not set.")
                )

            EventStoreDBClient.create(settings)
        }
        bindProviderOf(::EventStoreClientWrapper)
        bindProviderOf(::ResolvedEventMapper)

        bindSingletonOf(::EventMap)
        bindSet<EventMapDefinition>()

        bindSet<EventHandler>()
        bindSingletonOf(::EventHandlerMap)
        bindSet<EventHandlerDefinition>()
    }
