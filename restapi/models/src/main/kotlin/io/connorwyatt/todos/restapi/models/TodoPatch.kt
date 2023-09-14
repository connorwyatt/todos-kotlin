package io.connorwyatt.todos.restapi.models

import io.connorwyatt.todos.common.models.Optional
import io.connorwyatt.todos.common.models.Optional.Absent
import kotlinx.serialization.*

@Serializable data class TodoPatch(val title: Optional<String> = Absent)
