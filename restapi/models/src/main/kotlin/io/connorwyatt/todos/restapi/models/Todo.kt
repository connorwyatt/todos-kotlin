package io.connorwyatt.todos.restapi.models

import io.connorwyatt.todos.common.serialization.InstantSerializer
import java.time.Instant
import kotlinx.serialization.*

@Serializable
data class Todo(
    val id: String,
    val title: String,
    @Serializable(with = InstantSerializer::class) val addedAt: Instant,
    val isComplete: Boolean,
    @Serializable(with = InstantSerializer::class) val completedAt: Instant?,
)
