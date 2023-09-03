package io.connorwyatt.todos.restapi.client

import io.connorwyatt.todos.common.http.EmptyHttpResult
import io.connorwyatt.todos.common.http.HttpResult
import io.connorwyatt.todos.restapi.models.Todo
import io.connorwyatt.todos.restapi.models.TodoDefinition
import io.connorwyatt.todos.restapi.models.TodoReference

interface TodosClient {
    suspend fun retrieveTodos(): HttpResult<List<Todo>>

    suspend fun retrieveTodo(todoId: String): HttpResult<Todo>

    suspend fun addTodo(definition: TodoDefinition): HttpResult<TodoReference>

    suspend fun completeTodo(todoId: String): EmptyHttpResult
}
