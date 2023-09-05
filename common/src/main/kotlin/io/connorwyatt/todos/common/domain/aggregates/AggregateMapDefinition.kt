package io.connorwyatt.todos.common.domain.aggregates

import kotlin.reflect.KClass

data class AggregateMapDefinition<TAggregate : Aggregate>(
    internal val category: String,
    internal val clazz: KClass<out TAggregate>,
    internal val constructor: (String) -> TAggregate
)
