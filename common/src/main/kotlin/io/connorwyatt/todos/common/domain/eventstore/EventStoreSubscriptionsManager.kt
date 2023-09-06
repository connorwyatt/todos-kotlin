package io.connorwyatt.todos.common.domain.eventstore

import com.eventstore.dbclient.SubscribeToStreamOptions
import io.connorwyatt.todos.common.domain.eventhandlers.EventHandler
import io.connorwyatt.todos.common.domain.eventhandlers.EventHandlerMap
import io.connorwyatt.todos.common.domain.events.ResolvedEventMapper
import kotlinx.coroutines.*

class EventStoreSubscriptionsManager(
    private val eventStoreClientWrapper: EventStoreClientWrapper,
    private val eventHandlers: Set<EventHandler>,
    private val eventHandlerMap: EventHandlerMap,
    private val resolvedEventMapper: ResolvedEventMapper
) {
    private var jobs = emptyList<Job>()

    suspend fun start() {
        jobs =
            jobs.plus(
                eventHandlers.flatMap { eventHandler ->
                    val streamNames = eventHandlerMap.streamNamesFor(eventHandler::class)

                    streamNames.map { streamName ->
                        val subscribeToStreamOptions =
                            SubscribeToStreamOptions.get().resolveLinkTos(true).apply {
                                eventHandler.streamPosition(streamName)?.let { fromRevision(it) }
                                    ?: fromStart()
                            }

                        CoroutineScope(Dispatchers.IO).launch {
                            subscribe(streamName, eventHandler, subscribeToStreamOptions)
                        }
                    }
                }
            )
    }

    private fun subscribe(
        streamName: String,
        eventHandler: EventHandler,
        subscribeToStreamOptions: SubscribeToStreamOptions,
    ) {
        eventStoreClientWrapper.subscribeToStream(
            streamName,
            { _, resolvedEvent ->
                val eventEnvelope = resolvedEventMapper.map(resolvedEvent)
                runBlocking(Dispatchers.IO) {
                    eventHandler.handleEvent(
                        streamName,
                        eventEnvelope.event,
                        eventEnvelope.metadata
                    )
                }
            },
            subscribeToStreamOptions = subscribeToStreamOptions
        )
    }
}
