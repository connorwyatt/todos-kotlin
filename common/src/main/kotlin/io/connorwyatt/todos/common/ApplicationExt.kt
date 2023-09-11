package io.connorwyatt.todos.common

import io.connorwyatt.todos.common.domain.events.EventsRepository
import io.connorwyatt.todos.common.domain.eventstore.EventStoreConfiguration
import io.connorwyatt.todos.common.domain.eventstore.EventStoreSubscriptionsManager
import io.connorwyatt.todos.common.domain.inmemory.InMemoryEventsRepository
import io.connorwyatt.todos.common.messaging.RabbitMQConfiguration
import io.connorwyatt.todos.common.messaging.commands.bus.CommandBus
import io.connorwyatt.todos.common.messaging.commands.bus.InMemoryCommandBus
import io.connorwyatt.todos.common.messaging.commands.commandhandlers.RabbitMQSubscriptionsManager
import io.connorwyatt.todos.common.messaging.commands.queues.CommandQueueCreator
import io.ktor.server.application.*
import kotlinx.coroutines.launch
import org.kodein.di.*
import org.kodein.di.ktor.*

fun Application.configureEventStore(eventStoreConfiguration: EventStoreConfiguration) {
    if (!eventStoreConfiguration.useInMemoryEventStore) {
        val eventStoreSubscriptionsManager by closestDI().instance<EventStoreSubscriptionsManager>()

        eventStoreSubscriptionsManager.start()
    }

    val eventsRepository by closestDI().instance<EventsRepository>()

    (eventsRepository as? InMemoryEventsRepository)?.run { startEventPropagation() }
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
