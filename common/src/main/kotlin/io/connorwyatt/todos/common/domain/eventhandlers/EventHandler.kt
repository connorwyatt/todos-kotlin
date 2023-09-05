package io.connorwyatt.todos.common.domain.eventhandlers

import io.connorwyatt.todos.common.domain.events.Event
import io.connorwyatt.todos.common.domain.events.EventMetadata
import kotlin.reflect.KClass

abstract class EventHandler {
    private var handlers = mapOf<KClass<out Event>, suspend (Event, EventMetadata) -> Unit>()

    abstract suspend fun streamPosition(streamName: String): Long?

    abstract suspend fun updateStreamPosition(streamName: String, streamPosition: Long)

    protected fun <TEvent : Event> handle(
        eventType: KClass<TEvent>,
        handler: suspend (TEvent, EventMetadata) -> Unit
    ) {
        @Suppress("UNCHECKED_CAST")
        handlers =
            handlers.plus(
                eventType as KClass<out Event> to handler as suspend (Event, EventMetadata) -> Unit
            )
    }

    protected inline fun <reified TEvent : Event> handle(
        noinline handler: suspend (TEvent, EventMetadata) -> Unit
    ) {
        handle(TEvent::class, handler)
    }

    internal suspend fun handleEvent(streamName: String, event: Event, metadata: EventMetadata) {
        handlers[event::class]?.invoke(event, metadata)
        updateStreamPosition(streamName, metadata.streamPosition)
    }
}
