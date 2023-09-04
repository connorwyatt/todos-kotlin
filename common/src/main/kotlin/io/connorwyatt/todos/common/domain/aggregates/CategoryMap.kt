package io.connorwyatt.todos.common.domain.aggregates

import kotlin.reflect.KClass

class CategoryMap {
    private var map = mapOf<String, KClass<out Aggregate>>()

    fun <TAggregate : Aggregate> categoryFor(aggregate: TAggregate): String =
        categoryFor(aggregate::class)

    fun <TAggregate : Aggregate> categoryFor(clazz: KClass<TAggregate>): String =
        map.entries.singleOrNull { it.value == clazz }?.key
            ?: "Could not find category name for Aggregate class (${clazz.simpleName})."

    inline fun <reified TAggregate : Aggregate> categoryFor(): String =
        categoryFor(TAggregate::class)

    fun registerCategory(category: String, clazz: KClass<out Aggregate>): CategoryMap {
        map = map.plus(category to clazz)

        if (map.values.distinct().count() != map.count()) {
            throw Exception("Multiple CategoryMap entries registered for \"$category\".")
        }

        return this
    }

    inline fun <reified TAggregate : Aggregate> registerCategory(category: String): CategoryMap =
        registerCategory(category, TAggregate::class)
}
