package io.connorwyatt.todos.data.models

import io.connorwyatt.common.data.models.Versioned
import java.time.Instant

data class Todo(
    val id: String,
    val title: String,
    val addedAt: Instant,
    val isComplete: Boolean,
    val completedAt: Instant?,
    override val version: Long,
) : Versioned
