package io.connorwyatt.todos.common.domain.eventhandlers

import io.connorwyatt.todos.common.domain.streams.StreamDescriptor
import kotlin.reflect.KClass

class EventHandlerMap(private val definitions: Set<EventHandlerDefinition>) {
    internal fun streamDescriptorsFor(clazz: KClass<out EventHandler>): Set<StreamDescriptor> =
        definitions.filter { it.clazz == clazz }.map { it.streamDescriptor }.toSet()

    internal fun eventHandlersFor(
        streamDescriptor: StreamDescriptor
    ): Set<KClass<out EventHandler>> =
        definitions.filter { it.streamDescriptor == streamDescriptor }.map { it.clazz }.toSet()
}
