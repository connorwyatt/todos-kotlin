package io.connorwyatt.todos.restapi.models

import io.connorwyatt.common.optional.Optional
import io.connorwyatt.common.optional.Optional.Absent
import kotlinx.serialization.Serializable

@Serializable data class TodoPatch(val title: Optional<String> = Absent)
