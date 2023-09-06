package io.connorwyatt.todos.common.domain.aggregates

import io.connorwyatt.todos.common.domain.events.EventsRepository
import io.connorwyatt.todos.common.domain.streams.StreamNameUtilities
import kotlin.reflect.KClass

class AggregatesRepository(
    private val eventsRepository: EventsRepository,
    private val aggregateMap: AggregateMap
) {
    suspend fun <TAggregate : Aggregate> load(clazz: KClass<TAggregate>, id: String): TAggregate {
        val (category, _, constructor) = aggregateMap.definitionFor(clazz)
        val streamName = StreamNameUtilities.streamName(category, id)

        val aggregate = constructor.invoke(id)

        val events = eventsRepository.readStream(streamName)

        aggregate.applyEvents(events)

        return aggregate
    }

    suspend inline fun <reified TAggregate : Aggregate> load(streamName: String): TAggregate {
        return load(TAggregate::class, streamName)
    }

    suspend fun <TAggregate : Aggregate> save(aggregate: TAggregate) {
        if (aggregate.unsavedEvents.isEmpty()) {
            return
        }

        val (category) = aggregateMap.definitionFor(aggregate::class)

        val streamName = StreamNameUtilities.streamName(category, aggregate.id)

        eventsRepository.appendToStream(
            streamName,
            aggregate.unsavedEvents,
            aggregate.latestSavedEventVersion()
        )
    }
}
