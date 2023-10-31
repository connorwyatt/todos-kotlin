package io.connorwyatt.todos.common

import io.connorwyatt.common.eventstore.EventStoreConfiguration
import io.connorwyatt.common.eventstore.eventStoreDependenciesModule
import io.connorwyatt.common.http.httpDependenciesModule
import io.connorwyatt.common.rabbitmq.RabbitMQConfiguration
import io.connorwyatt.common.rabbitmq.rabbitMQDependenciesModule
import io.connorwyatt.common.time.timeDependenciesModule
import io.connorwyatt.todos.common.data.mongodb.MongoDBConfiguration
import io.connorwyatt.todos.common.data.mongodb.mongoDBDependenciesModule
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
