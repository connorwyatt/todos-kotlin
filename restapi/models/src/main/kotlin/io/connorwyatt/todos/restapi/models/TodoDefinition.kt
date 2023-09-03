package io.connorwyatt.todos.restapi.models

import kotlinx.serialization.Serializable

@Serializable
data class TodoDefinition(
    val title: String,
)
