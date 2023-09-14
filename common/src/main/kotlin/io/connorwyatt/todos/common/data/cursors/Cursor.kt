package io.connorwyatt.todos.common.data.cursors

data class Cursor(
    val subscriptionName: String,
    val streamName: String,
    val lastHandledStreamPosition: Long,
)
