package io.connorwyatt.todos.data.models

import java.time.Instant

data class Todo(
    val id: String,
    val title: String,
    val addedAt: Instant,
    val isComplete: Boolean,
    val completedAt: Instant?,
)
