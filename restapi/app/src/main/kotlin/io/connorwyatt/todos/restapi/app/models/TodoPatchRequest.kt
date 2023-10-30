package io.connorwyatt.todos.restapi.app.models

import io.connorwyatt.common.optional.Optional
import io.connorwyatt.common.optional.Optional.Absent
import io.connorwyatt.common.optional.Optional.Present
import io.connorwyatt.todos.restapi.models.TodoPatch
import kotlinx.serialization.Serializable

@Serializable
data class TodoPatchRequest(val title: Optional<String?> = Absent) {
    fun toModel(): TodoPatch = TodoPatch(title.ifPresent { Present(checkNotNull(it)) } ?: Absent)
}
