package io.connorwyatt.todos.common.domain.eventstore

import com.eventstore.dbclient.SubscribeToStreamOptions
import io.connorwyatt.todos.common.domain.eventhandlers.EventHandler
import io.connorwyatt.todos.common.domain.eventhandlers.EventHandlerMap
import io.connorwyatt.todos.common.domain.events.ResolvedEventMapper
import io.connorwyatt.todos.common.domain.streams.StreamDescriptor
import kotlinx.coroutines.*

class EventStoreSubscriptionsManager(
    private val eventStoreClientWrapper: EventStoreClientWrapper,
    private val eventHandlers: Set<EventHandler>,
    private val eventHandlerMap: EventHandlerMap,
    private val resolvedEventMapper: ResolvedEventMapper
) {
    private var jobs = emptyList<Job>()
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    fun start() {
        coroutineScope.launch {
            jobs =
                jobs.plus(
                    eventHandlers.flatMap { eventHandler ->
                        val streamDescriptors =
                            eventHandlerMap.streamDescriptorsFor(eventHandler::class)

                        streamDescriptors.map { streamDescriptor ->
                            val subscribeToStreamOptions =
                                SubscribeToStreamOptions.get().resolveLinkTos(true).apply {
                                    eventHandler.streamPosition(streamDescriptor)?.let {
                                        fromRevision(it)
                                    }
                                        ?: fromStart()
                                }

                            launch {
                                subscribe(streamDescriptor, eventHandler, subscribeToStreamOptions)
                            }
                        }
                    }
                )
        }
    }

    private fun subscribe(
        streamDescriptor: StreamDescriptor,
        eventHandler: EventHandler,
        subscribeToStreamOptions: SubscribeToStreamOptions,
    ) {
        eventStoreClientWrapper.subscribeToStream(
            streamDescriptor,
            { _, resolvedEvent ->
                val eventEnvelope = resolvedEventMapper.map(resolvedEvent)
                runBlocking(Dispatchers.IO) {
                    eventHandler.handleEvent(
                        streamDescriptor,
                        eventEnvelope.event,
                        eventEnvelope.metadata
                    )
                }
            },
            subscribeToStreamOptions = subscribeToStreamOptions
        )
    }
}
