package io.connorwyatt.todos.common

import io.connorwyatt.todos.common.domain.events.EventsRepository
import io.connorwyatt.todos.common.domain.eventstore.EventStoreConfiguration
import io.connorwyatt.todos.common.domain.eventstore.EventStoreSubscriptionsManager
import io.connorwyatt.todos.common.domain.inmemory.InMemoryEventsRepository
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

    launch { (eventsRepository as? InMemoryEventsRepository)?.run { startEventPropagation() } }
}

fun Application.configureRabbitMQ() {
    val commandQueueCreator by closestDI().instance<CommandQueueCreator>()
    val rabbitMQSubscriptionsManager by closestDI().instance<RabbitMQSubscriptionsManager>()

    launch {
        commandQueueCreator.createQueues()

        rabbitMQSubscriptionsManager.start()
    }
}
