package io.connorwyatt.todos.common

import io.connorwyatt.todos.common.domain.eventstore.EventStoreSubscriptionsManager
import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.*
import org.kodein.di.ktor.*

fun Application.configureCommon() {
    val eventStoreSubscriptionsManager by
        this.closestDI().instance<EventStoreSubscriptionsManager>()

    launch(Dispatchers.IO) { eventStoreSubscriptionsManager.start() }
}
