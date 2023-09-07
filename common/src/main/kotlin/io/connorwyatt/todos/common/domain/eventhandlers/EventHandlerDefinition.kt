package io.connorwyatt.todos.common.domain.eventhandlers

import io.connorwyatt.todos.common.domain.streams.StreamDescriptor
import kotlin.reflect.KClass

class EventHandlerDefinition(
    internal val streamDescriptor: StreamDescriptor,
    internal val clazz: KClass<out EventHandler>
)
