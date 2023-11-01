package io.connorwyatt.todos.common

import io.connorwyatt.common.eventstore.configuration.EventStoreConfiguration
import io.connorwyatt.common.eventstore.kodein.eventStoreDependenciesModule
import io.connorwyatt.common.http.httpDependenciesModule
import io.connorwyatt.common.mongodb.configuration.MongoDBConfiguration
import io.connorwyatt.common.mongodb.kodein.mongoDBDependenciesModule
import io.connorwyatt.common.rabbitmq.configuration.RabbitMQConfiguration
import io.connorwyatt.common.rabbitmq.kodein.rabbitMQDependenciesModule
import io.connorwyatt.common.time.timeDependenciesModule
import org.kodein.di.*

fun commonDependenciesModule(
    eventStoreConfiguration: EventStoreConfiguration,
    mongoDBConfiguration: MongoDBConfiguration,
    rabbitMQConfiguration: RabbitMQConfiguration,
): DI.Module =
    DI.Module(name = ::commonDependenciesModule.name) {
        import(eventStoreDependenciesModule(eventStoreConfiguration))
        import(httpDependenciesModule)
        import(mongoDBDependenciesModule(mongoDBConfiguration))
        import(rabbitMQDependenciesModule(rabbitMQConfiguration))
        import(timeDependenciesModule)
    }
