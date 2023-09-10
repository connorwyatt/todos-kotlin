package io.connorwyatt.todos.common

import io.connorwyatt.todos.common.domain.events.EventsRepository
import io.connorwyatt.todos.common.domain.eventstore.EventStoreConfiguration
import io.connorwyatt.todos.common.domain.eventstore.EventStoreSubscriptionsManager
import io.connorwyatt.todos.common.domain.inmemory.InMemoryEventsRepository
import io.connorwyatt.todos.common.messaging.commands.queues.CommandQueueCreator
import io.ktor.server.application.*
import kotlinx.coroutines.launch
import org.kodein.di.*
import org.kodein.di.ktor.*

fun Application.configureEventStore(eventStoreConfiguration: EventStoreConfiguration) {
    if (!eventStoreConfiguration.useInMemoryEventStore) {
        val eventStoreSubscriptionsManager by
            this.closestDI().instance<EventStoreSubscriptionsManager>()

        launch { eventStoreSubscriptionsManager.start() }
    }

    val eventsRepository by closestDI().instance<EventsRepository>()

    (eventsRepository as? InMemoryEventsRepository)?.run { launch { startEventPropagation() } }
}

fun Application.configureRabbitMQ() {
    val commandQueueCreator by closestDI().instance<CommandQueueCreator>()

    launch { commandQueueCreator.createQueues() }
}
