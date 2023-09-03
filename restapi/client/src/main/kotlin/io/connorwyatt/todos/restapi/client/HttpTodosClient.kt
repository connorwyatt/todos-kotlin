package io.connorwyatt.todos.restapi.client

import io.connorwyatt.todos.common.http.EmptyHttpResult
import io.connorwyatt.todos.common.http.HttpResult
import io.connorwyatt.todos.restapi.models.Todo
import io.connorwyatt.todos.restapi.models.TodoDefinition
import io.connorwyatt.todos.restapi.models.TodoReference
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class HttpTodosClient(private val client: HttpClient) : TodosClient {
    override suspend fun retrieveTodos(): HttpResult<List<Todo>> =
        client.get("todos").let { HttpResult.fromResponse(it) }

    override suspend fun retrieveTodo(todoId: String): HttpResult<Todo> =
        client.get("todos/$todoId").let { HttpResult.fromResponse(it) }

    override suspend fun addTodo(definition: TodoDefinition): HttpResult<TodoReference> =
        client
            .post("todos") {
                contentType(ContentType.Application.Json)
                setBody(definition)
            }
            .let { HttpResult.fromResponse(it) }

    override suspend fun completeTodo(todoId: String): EmptyHttpResult =
        client
            .post("todos/$todoId/actions/complete") { contentType(ContentType.Application.Json) }
            .let { EmptyHttpResult.fromResponse(it) }
}
