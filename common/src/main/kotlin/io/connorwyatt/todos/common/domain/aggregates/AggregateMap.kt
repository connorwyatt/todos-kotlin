package io.connorwyatt.todos.common.domain.aggregates

import kotlin.reflect.KClass

class AggregateMap {
    private var definitions = emptyList<AggregateDefinition<out Aggregate>>()

    fun <TAggregate : Aggregate> registerAggregate(
        category: String,
        clazz: KClass<TAggregate>,
        constructor: (String) -> TAggregate
    ): AggregateMap {
        definitions = definitions.plus(AggregateDefinition(category, clazz, constructor))

        if (definitions.distinctBy { it.category }.count() != definitions.count()) {
            throw Exception("Multiple AggregateMap definitions registered for \"$category\".")
        }

        if (definitions.distinctBy { it.clazz }.count() != definitions.count()) {
            throw Exception(
                "Multiple AggregateMap definitions registered for \"${clazz.simpleName}\"."
            )
        }

        return this
    }

    inline fun <reified TAggregate : Aggregate> registerAggregate(
        category: String,
        noinline constructor: (String) -> TAggregate
    ): AggregateMap = registerAggregate(category, TAggregate::class, constructor)

    @Suppress("UNCHECKED_CAST")
    internal fun <TAggregate : Aggregate> definitionFor(
        clazz: KClass<TAggregate>
    ): AggregateDefinition<TAggregate> =
        definitions.single { it.clazz == clazz } as AggregateDefinition<TAggregate>

    internal inline fun <reified TAggregate : Aggregate> definitionFor():
        AggregateDefinition<TAggregate> = definitionFor(TAggregate::class)

    internal data class AggregateDefinition<TAggregate : Aggregate>(
        val category: String,
        val clazz: KClass<out TAggregate>,
        val constructor: (String) -> TAggregate
    )
}
