package io.connorwyatt.todos.domain

import io.connorwyatt.common.eventstore.aggregates.Aggregate
import io.connorwyatt.todos.common.models.Optional
import io.connorwyatt.todos.common.models.Optional.Present
import io.connorwyatt.todos.domain.events.TodoAdded
import io.connorwyatt.todos.domain.events.TodoCompleted
import io.connorwyatt.todos.domain.events.TodoUpdated

class Todo(id: String) : Aggregate(id) {
    private var isAdded = false
    private var isComplete = false

    init {
        handle<TodoAdded>(::apply)
        handle<TodoUpdated>(::apply)
        handle<TodoCompleted>(::apply)
    }

    fun addTodo(title: String) {
        if (isAdded) {
            throw Exception("Todo already added.")
        }

        raiseEvent(TodoAdded(id, title))
    }

    fun updateTodo(title: Optional<String>) {
        if (isComplete) {
            throw Exception("Cannot update a completed Todo.")
        }

        if (listOf(title).filterIsInstance<Present<*>>().isEmpty()) {
            return
        }

        raiseEvent(TodoUpdated(id, title))
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

    private fun apply(event: TodoUpdated) {}

    private fun apply(event: TodoCompleted) {
        isComplete = true
    }

    companion object {
        const val category = "todos"
    }
}
