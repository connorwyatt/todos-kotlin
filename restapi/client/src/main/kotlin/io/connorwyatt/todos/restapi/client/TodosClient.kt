package io.connorwyatt.todos.restapi.client

import io.connorwyatt.common.http.EmptyHttpResult
import io.connorwyatt.common.http.HttpResult
import io.connorwyatt.todos.restapi.models.Todo
import io.connorwyatt.todos.restapi.models.TodoDefinition
import io.connorwyatt.todos.restapi.models.TodoPatch
import io.connorwyatt.todos.restapi.models.TodoReference

interface TodosClient {
    suspend fun retrieveTodos(): HttpResult<List<Todo>>

    suspend fun retrieveTodo(todoId: String): HttpResult<Todo>

    suspend fun addTodo(definition: TodoDefinition): HttpResult<TodoReference>

    suspend fun updateTodo(todoId: String, patch: TodoPatch): EmptyHttpResult

    suspend fun completeTodo(todoId: String): EmptyHttpResult
}
