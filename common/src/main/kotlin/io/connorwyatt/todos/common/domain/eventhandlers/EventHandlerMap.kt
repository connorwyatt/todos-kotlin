package io.connorwyatt.todos.common.domain.eventhandlers

import kotlin.reflect.KClass

class EventHandlerMap {
    private var map = mapOf<String, KClass<out EventHandler>>()

    fun streamNamesFor(clazz: KClass<out EventHandler>): Set<String> =
        map.filterValues { it == clazz }.map { it.key }.toSet()

    fun registerEventHandler(
        streamName: String,
        eventHandler: KClass<out EventHandler>
    ): EventHandlerMap {
        map = map.plus(streamName to eventHandler)

        if (map.entries.distinct().count() != map.count()) {
            throw Exception(
                "Multiple EventHandlerMap entries registered for \"$streamName\" and \"${eventHandler.simpleName}\"."
            )
        }

        return this
    }

    inline fun <reified TEventHandler : EventHandler> registerEventHandler(
        streamName: String
    ): EventHandlerMap = registerEventHandler(streamName, TEventHandler::class)
}
