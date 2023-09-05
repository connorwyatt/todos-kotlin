package io.connorwyatt.todos.common.domain.eventhandlers

import kotlin.reflect.KClass

class EventHandlerDefinition(
    internal val streamName: String,
    internal val clazz: KClass<out EventHandler>
)
