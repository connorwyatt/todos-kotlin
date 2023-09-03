package io.connorwyatt.todos.data

import io.connorwyatt.todos.data.models.Todo

interface TodosRepository {
    suspend fun searchTodos(): List<Todo>

    suspend fun getTodo(todoId: String): Todo?

    suspend fun insertTodo(todo: Todo)

    suspend fun updateTodo(todo: Todo)
}
