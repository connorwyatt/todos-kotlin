package io.connorwyatt.todos.common.domain.events

import kotlin.reflect.KClass

class EventMapDefinition(
    internal val versionedEventType: VersionedEventType,
    internal val clazz: KClass<out Event>
)
