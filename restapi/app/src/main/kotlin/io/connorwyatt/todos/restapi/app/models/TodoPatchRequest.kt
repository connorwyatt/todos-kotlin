package io.connorwyatt.todos.restapi.app.models

import io.connorwyatt.todos.common.models.Optional
import io.connorwyatt.todos.common.models.Optional.Absent
import io.connorwyatt.todos.common.models.Optional.Present
import io.connorwyatt.todos.restapi.models.TodoPatch
import kotlinx.serialization.*

@Serializable
data class TodoPatchRequest(val title: Optional<String?> = Absent) {
    fun toModel(): TodoPatch = TodoPatch(title.ifPresent { Present(checkNotNull(it)) } ?: Absent)
}
