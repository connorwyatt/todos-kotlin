package io.connorwyatt.todos.common.domain.aggregates

import kotlin.reflect.KClass

class AggregateMap {
    private var map = mapOf<String, KClass<out Aggregate>>()

    fun <TAggregate : Aggregate> categoryFor(aggregate: TAggregate): String =
        categoryFor(aggregate::class)

    fun <TAggregate : Aggregate> categoryFor(clazz: KClass<TAggregate>): String =
        map.entries.singleOrNull { it.value == clazz }?.key
            ?: "Could not find category name for Aggregate class (${clazz.simpleName})."

    inline fun <reified TAggregate : Aggregate> categoryFor(): String =
        categoryFor(TAggregate::class)

    fun registerAggregate(category: String, clazz: KClass<out Aggregate>): AggregateMap {
        map = map.plus(category to clazz)

        if (map.values.distinct().count() != map.count()) {
            throw Exception("Multiple AggregateMap entries registered for \"$category\".")
        }

        return this
    }

    inline fun <reified TAggregate : Aggregate> registerAggregate(category: String): AggregateMap =
        registerAggregate(category, TAggregate::class)
}
