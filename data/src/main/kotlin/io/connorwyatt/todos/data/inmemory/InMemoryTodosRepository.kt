package io.connorwyatt.todos.data.inmemory

import io.connorwyatt.todos.data.TodosRepository
import io.connorwyatt.todos.data.models.Todo

class InMemoryTodosRepository : TodosRepository {
    private var todos = mapOf<String, Todo>()

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
}
