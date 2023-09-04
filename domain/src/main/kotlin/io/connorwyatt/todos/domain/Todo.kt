package io.connorwyatt.todos.domain

import io.connorwyatt.todos.common.domain.aggregates.Aggregate
import io.connorwyatt.todos.domain.events.TodoAdded
import io.connorwyatt.todos.domain.events.TodoCompleted

class Todo(id: String) : Aggregate(id) {
    private var isAdded = false
    private var isComplete = false

    init {
        registerApplyFunction<TodoAdded>(::apply)
        registerApplyFunction<TodoCompleted>(::apply)
    }

    fun addTodo(title: String) {
        if (isAdded) {
            throw Exception("Todo already added.")
        }

        raiseEvent(TodoAdded(id, title))
    }

    fun completeTodo() {
        if (isComplete) {
            return
        }

        raiseEvent(TodoCompleted(id))
    }

    private fun apply(event: TodoAdded) {
        isAdded = true
    }

    private fun apply(event: TodoCompleted) {
        isComplete = true
    }

    companion object {
        val category = "todos"
    }
}
