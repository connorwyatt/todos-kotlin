package io.connorwyatt.todos.common.domain

import com.eventstore.dbclient.EventStoreDBClient
import com.eventstore.dbclient.EventStoreDBConnectionString
import io.connorwyatt.todos.common.domain.aggregates.AggregatesRepository
import io.connorwyatt.todos.common.domain.aggregates.CategoryMap
import io.connorwyatt.todos.common.domain.events.EventMap
import io.connorwyatt.todos.common.domain.events.EventsRepository
import io.connorwyatt.todos.common.domain.events.eventstore.EventStoreClientWrapper
import org.kodein.di.*

val domainDependenciesModule by
    DI.Module {
        bind<AggregatesRepository> { singleton { new(::AggregatesRepository) } }
        bind<CategoryMap> { singleton { new(::CategoryMap) } }

        val settings =
            EventStoreDBConnectionString.parseOrThrow(
                // TODO: Move this to config.
                "esdb://admin:changeit@localhost:2113?tls=false"
            )

        bind<EventsRepository> { provider { new(::EventsRepository) } }
        bind<EventStoreDBClient> { provider { EventStoreDBClient.create(settings) } }
        bind<EventStoreClientWrapper> { provider { new(::EventStoreClientWrapper) } }
        bind<EventMap> { singleton { new(::EventMap) } }
    }
