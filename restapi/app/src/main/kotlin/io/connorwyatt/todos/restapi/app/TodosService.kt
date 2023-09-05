package io.connorwyatt.todos.restapi.app

import io.connorwyatt.todos.common.time.clock.Clock
import io.connorwyatt.todos.data.TodosRepository
import io.connorwyatt.todos.data.models.Todo as DataTodo
import io.connorwyatt.todos.restapi.app.mapping.TodoMapper
import io.connorwyatt.todos.restapi.models.Todo
import io.connorwyatt.todos.restapi.models.TodoDefinition
import java.util.*

class TodosService(
    private val repository: TodosRepository,
    private val clock: Clock,
    private val mapper: TodoMapper
) {
    suspend fun retrieveTodos(): List<Todo> =
        repository.searchTodos().map(mapper::fromDataModelToRestApiModel)

    suspend fun retrieveTodo(todoId: String): Todo? =
        repository.getTodo(todoId)?.let(mapper::fromDataModelToRestApiModel)

    suspend fun addTodo(definition: TodoDefinition): String {
        val id = UUID.randomUUID().toString()

        repository.insertTodo(DataTodo(id, definition.title, clock.now(), false, null, 0))

        return id
    }

    suspend fun completeTodo(todoId: String) {
        val todo = repository.getTodo(todoId) ?: throw Exception("Todo does not exist.")

        repository.updateTodo(todo.copy(isComplete = true, completedAt = clock.now()))
    }
}
