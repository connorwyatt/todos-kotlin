package io.connorwyatt.todos.common.models

interface ProblemResponse {
    val type: String
    val title: String
    val status: Int
    val detail: String
}
