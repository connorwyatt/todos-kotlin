package io.connorwyatt.todos.restapi.app.models

import io.connorwyatt.todos.restapi.models.TodoDefinition
import kotlinx.serialization.*

@Serializable
data class TodoDefinitionRequest(val title: String? = null) {
    fun toModel() = TodoDefinition(title!!)
}
