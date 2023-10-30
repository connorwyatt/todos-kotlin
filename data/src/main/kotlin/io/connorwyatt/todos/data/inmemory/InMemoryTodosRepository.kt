package io.connorwyatt.todos.data.inmemory

import io.connorwyatt.common.eventstore.eventhandlers.EventHandler.Cursor
import io.connorwyatt.todos.data.TodosRepository
import io.connorwyatt.todos.data.models.Todo

class InMemoryTodosRepository : TodosRepository {
    private var todos = mapOf<String, Todo>()
    private var cursors = listOf<Cursor>()

    override suspend fun searchTodos(): List<Todo> {
        return todos.values.toList()
    }

    override suspend fun getTodo(todoId: String): Todo? {
        return todos[todoId]
    }

    override suspend fun insertTodo(todo: Todo) {
        todos = todos.plus(todo.id to todo)
    }

    override suspend fun updateTodo(todo: Todo) {
        todos = todos.mapValues { (key, value) -> if (key == todo.id) todo else value }
    }

    override suspend fun getStreamPosition(subscriptionName: String, streamName: String): Long? =
        cursors
            .singleOrNull { it.subscriptionName == subscriptionName && it.streamName == streamName }
            ?.lastHandledStreamPosition

    override suspend fun updateStreamPosition(cursor: Cursor) {
        val hasCursor = cursors.contains(cursor)

        if (!hasCursor) {
            cursors = cursors.plus(cursor)
            return
        }

        cursors = cursors.map { if (it == cursor) cursor else it }
    }
}
