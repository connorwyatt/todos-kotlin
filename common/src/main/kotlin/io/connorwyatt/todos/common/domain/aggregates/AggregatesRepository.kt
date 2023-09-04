package io.connorwyatt.todos.common.domain.aggregates

import io.connorwyatt.todos.common.domain.events.EventsRepository
import kotlin.reflect.KClass
import kotlin.reflect.full.createType

class AggregatesRepository(
    private val eventsRepository: EventsRepository,
    private val categoryMap: CategoryMap
) {
    suspend fun <TAggregate : Aggregate> load(clazz: KClass<TAggregate>, id: String): TAggregate {
        val category = categoryMap.categoryFor(clazz)

        val streamName = StreamNameUtilities.streamName(category, id)

        val events = eventsRepository.readStream(streamName)

        val aggregate = constructAggregate(clazz, id)

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

        val category = categoryMap.categoryFor(aggregate)

        val streamName = StreamNameUtilities.streamName(category, aggregate.id)

        eventsRepository.appendToStream(
            streamName,
            aggregate.unsavedEvents,
            aggregate.latestSavedEventVersion()
        )
    }

    // TODO: Replace reflection.
    private fun <TAggregate : Aggregate> constructAggregate(clazz: KClass<TAggregate>, id: String) =
        clazz.constructors
            .single {
                it.parameters.count() == 1 &&
                    it.parameters.single().type == String::class.createType()
            }
            .call(id)
}
