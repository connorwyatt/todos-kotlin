package io.connorwyatt.todos.common.domain

import io.connorwyatt.todos.common.domain.eventhandlers.EventHandler
import org.kodein.di.*
import org.kodein.di.bindings.*

fun <TEventHandler : EventHandler> DI.Builder.bindEventHandler(
    constructor: NoArgBindingDI<*>.() -> TEventHandler
) {
    bind<EventHandler> { singleton { constructor() } }
}
