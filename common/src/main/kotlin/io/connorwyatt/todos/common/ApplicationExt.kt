package io.connorwyatt.todos.common

import io.connorwyatt.todos.common.data.mongodb.MongoDBInitializer
import io.connorwyatt.todos.common.messaging.RabbitMQConfiguration
import io.connorwyatt.todos.common.messaging.commands.bus.CommandBus
import io.connorwyatt.todos.common.messaging.commands.bus.InMemoryCommandBus
import io.connorwyatt.todos.common.messaging.commands.commandhandlers.RabbitMQSubscriptionsManager
import io.connorwyatt.todos.common.messaging.commands.queues.CommandQueueCreator
import io.ktor.server.application.*
import kotlinx.coroutines.launch
import org.kodein.di.*
import org.kodein.di.ktor.*

suspend fun Application.configureMongoDB() {
    val mongoDBInitializer by closestDI().instanceOrNull<MongoDBInitializer>()

    mongoDBInitializer?.initialize()
}

fun Application.configureRabbitMQ(rabbitMQConfiguration: RabbitMQConfiguration) {
    val commandQueueCreator by closestDI().instance<CommandQueueCreator>()

    launch {
        commandQueueCreator.createQueues()

        if (!rabbitMQConfiguration.useInMemoryRabbitMQ) {
            val rabbitMQSubscriptionsManager by closestDI().instance<RabbitMQSubscriptionsManager>()
            rabbitMQSubscriptionsManager.start()
        }

        val commandBus by closestDI().instance<CommandBus>()

        (commandBus as? InMemoryCommandBus)?.run { startCommandPropagation() }
    }
}
