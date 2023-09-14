package io.connorwyatt.todos.projector

import io.connorwyatt.todos.common.data.cursors.Cursor
import io.connorwyatt.todos.common.domain.eventhandlers.SubscriptionName
import io.connorwyatt.todos.common.domain.events.EventMetadata
import io.connorwyatt.todos.common.domain.projector.Projector
import io.connorwyatt.todos.common.domain.streams.StreamDescriptor
import io.connorwyatt.todos.data.TodosRepository
import io.connorwyatt.todos.data.models.Todo
import io.connorwyatt.todos.domain.events.TodoAdded
import io.connorwyatt.todos.domain.events.TodoCompleted

@SubscriptionName("todos-projector")
class TodosProjector(private val repository: TodosRepository) : Projector() {

    init {
        handle<TodoAdded>(::handle)
        handle<TodoCompleted>(::handle)
    }

    override suspend fun streamPosition(
        subscriptionName: String,
        streamDescriptor: StreamDescriptor,
    ): Long? = repository.getStreamPosition(subscriptionName(), streamDescriptor.streamName)

    override suspend fun updateStreamPosition(cursor: Cursor) {
        repository.updateStreamPosition(cursor)
    }

    private suspend fun handle(event: TodoAdded, metadata: EventMetadata) {
        val existingTodo = repository.getTodo(event.todoId)
        if (existingTodo != null) {
            return
        }

        val todo =
            Todo(
                event.todoId,
                event.title,
                metadata.timestamp,
                false,
                null,
                metadata.streamPosition
            )

        repository.insertTodo(todo)
    }

    private suspend fun handle(event: TodoCompleted, metadata: EventMetadata) {
        val existingTodo =
            repository.getTodo(event.todoId)
                ?: throw Exception("Could not find Todo \"${event.todoId}\".")

        if (isEventHandled(existingTodo, metadata)) {
            return
        }

        val updatedTodo =
            existingTodo.copy(
                isComplete = true,
                completedAt = metadata.timestamp,
                version = metadata.streamPosition
            )

        repository.updateTodo(updatedTodo)
    }
}
