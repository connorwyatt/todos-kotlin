package io.connorwyatt.todos.common

import io.connorwyatt.todos.common.domain.domainDependenciesModule
import io.connorwyatt.todos.common.domain.eventstore.EventStoreConfiguration
import io.connorwyatt.todos.common.http.httpDependenciesModule
import io.connorwyatt.todos.common.time.timeDependenciesModule
import org.kodein.di.*

fun commonDependenciesModule(eventStoreConfiguration: EventStoreConfiguration): DI.Module =
    DI.Module(name = ::commonDependenciesModule.name) {
        import(domainDependenciesModule(eventStoreConfiguration))
        import(httpDependenciesModule)
        import(timeDependenciesModule)
    }
