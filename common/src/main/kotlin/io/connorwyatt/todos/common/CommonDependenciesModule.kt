package io.connorwyatt.todos.common

import io.connorwyatt.todos.common.data.configuration.DataConfiguration
import io.connorwyatt.todos.common.data.mongodb.MongoDBConfiguration
import io.connorwyatt.todos.common.data.mongodb.mongoDBDependenciesModule
import io.connorwyatt.todos.common.domain.domainDependenciesModule
import io.connorwyatt.todos.common.domain.eventstore.EventStoreConfiguration
import io.connorwyatt.todos.common.http.httpDependenciesModule
import io.connorwyatt.todos.common.messaging.RabbitMQConfiguration
import io.connorwyatt.todos.common.messaging.messagingDependenciesModule
import io.connorwyatt.todos.common.time.timeDependenciesModule
import org.kodein.di.*

fun commonDependenciesModule(
    dataConfiguration: DataConfiguration,
    eventStoreConfiguration: EventStoreConfiguration,
    mongoDBConfiguration: MongoDBConfiguration,
    rabbitMQConfiguration: RabbitMQConfiguration,
): DI.Module =
    DI.Module(name = ::commonDependenciesModule.name) {
        import(domainDependenciesModule(eventStoreConfiguration))
        import(httpDependenciesModule)
        import(messagingDependenciesModule(rabbitMQConfiguration))
        import(mongoDBDependenciesModule(dataConfiguration, mongoDBConfiguration))
        import(timeDependenciesModule)
    }
