package io.connorwyatt.todos.common.domain

import com.eventstore.dbclient.EventStoreDBClient
import com.eventstore.dbclient.EventStoreDBConnectionString
import io.connorwyatt.todos.common.domain.aggregates.AggregateMap
import io.connorwyatt.todos.common.domain.aggregates.AggregatesRepository
import io.connorwyatt.todos.common.domain.events.EventMap
import io.connorwyatt.todos.common.domain.events.EventsRepository
import io.connorwyatt.todos.common.domain.events.eventstore.EventStoreClientWrapper
import org.kodein.di.*

val domainDependenciesModule by
    DI.Module {
        bindSingletonOf(::AggregatesRepository)
        bindSingletonOf(::AggregateMap)

        val settings =
            EventStoreDBConnectionString.parseOrThrow(
                // TODO: Move this to config.
                "esdb://admin:changeit@localhost:2113?tls=false"
            )

        bindProviderOf(::EventsRepository)
        bindProvider<EventStoreDBClient> { EventStoreDBClient.create(settings) }
        bindProviderOf(::EventStoreClientWrapper)
        bindSingletonOf(::EventMap)
    }
