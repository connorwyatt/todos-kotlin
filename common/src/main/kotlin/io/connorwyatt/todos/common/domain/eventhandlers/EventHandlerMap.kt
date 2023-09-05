package io.connorwyatt.todos.common.domain.eventhandlers

import kotlin.reflect.KClass

class EventHandlerMap(private val definitions: Set<EventHandlerDefinition>) {
    internal fun streamNamesFor(clazz: KClass<out EventHandler>): Set<String> =
        definitions.filter { it.clazz == clazz }.map { it.streamName }.toSet()
}
