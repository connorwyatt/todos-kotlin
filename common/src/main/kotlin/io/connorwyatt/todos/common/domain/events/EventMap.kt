package io.connorwyatt.todos.common.domain.events

import kotlin.reflect.KClass

class EventMap {
    private var map = mapOf<VersionedEventType, KClass<out Event>>()

    fun eventClass(versionedEventType: VersionedEventType): KClass<out Event> {
        return map[versionedEventType]
            ?: throw Exception(
                "Could not find Event class for VersionedEventType (${versionedEventType})."
            )
    }

    fun versionedEventType(event: Event): VersionedEventType {
        return versionedEventType(event::class)
    }

    fun versionedEventType(eventClass: KClass<out Event>): VersionedEventType {
        return map.entries.singleOrNull { it.value == eventClass }?.key
            ?: throw Exception(
                "Could not find VersionedEventType for Event class (${eventClass.simpleName})."
            )
    }

    fun registerEvent(versionedEventType: VersionedEventType, clazz: KClass<out Event>): EventMap {
        map = map.plus(versionedEventType to clazz)

        if (map.values.distinct().count() != map.count()) {
            throw Exception("Multiple EventMap entries registered for \"$versionedEventType\".")
        }

        return this
    }

    inline fun <reified TEvent : Event> registerEvent(
        versionedEventType: VersionedEventType
    ): EventMap = registerEvent(versionedEventType, TEvent::class)
}
