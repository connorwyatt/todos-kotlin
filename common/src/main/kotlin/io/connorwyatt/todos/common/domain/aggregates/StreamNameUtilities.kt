package io.connorwyatt.todos.common.domain.aggregates

object StreamNameUtilities {
    fun streamName(category: String, id: String) = "$category-$id"
}
