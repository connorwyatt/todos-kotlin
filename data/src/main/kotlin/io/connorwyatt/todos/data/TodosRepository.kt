package io.connorwyatt.todos.data

import io.connorwyatt.todos.common.data.cursors.Cursor
import io.connorwyatt.todos.data.models.Todo

interface TodosRepository {
    suspend fun searchTodos(): List<Todo>

    suspend fun getTodo(todoId: String): Todo?

    suspend fun insertTodo(todo: Todo)

    suspend fun updateTodo(todo: Todo)

    suspend fun getStreamPosition(subscriptionName: String, streamName: String): Long?

    suspend fun updateStreamPosition(cursor: Cursor)
}
