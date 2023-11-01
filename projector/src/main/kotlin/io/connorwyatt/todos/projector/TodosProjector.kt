package io.connorwyatt.todos.projector

import io.connorwyatt.common.data.models.Versioned
import io.connorwyatt.common.eventstore.eventhandlers.EventHandler
import io.connorwyatt.common.eventstore.eventhandlers.SubscriptionName
import io.connorwyatt.common.eventstore.events.EventMetadata
import io.connorwyatt.common.eventstore.streams.StreamDescriptor
import io.connorwyatt.common.optional.Optional.Present
import io.connorwyatt.todos.data.TodosRepository
import io.connorwyatt.todos.data.models.Todo
import io.connorwyatt.todos.domain.events.TodoAdded
import io.connorwyatt.todos.domain.events.TodoCompleted
import io.connorwyatt.todos.domain.events.TodoUpdated

@SubscriptionName("todos-projector")
class TodosProjector(private val repository: TodosRepository) : EventHandler() {
    init {
        handle<TodoAdded>(::handle)
        handle<TodoUpdated>(::handle)
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

    private suspend fun handle(event: TodoUpdated, metadata: EventMetadata) {
        val existingTodo =
            repository.getTodo(event.todoId)
                ?: throw Exception("Could not find Todo \"${event.todoId}\".")

        if (isEventHandled(existingTodo, metadata)) {
            return
        }

        var updatedTodo = existingTodo.copy(version = metadata.streamPosition)

        (event.title as? Present<String>)?.let { updatedTodo = updatedTodo.copy(title = it.value) }

        repository.updateTodo(updatedTodo)
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

    // TODO: Abstract this.
    private fun isEventHandled(versioned: Versioned, metadata: EventMetadata): Boolean {
        val expectedVersion = metadata.streamPosition - 1

        return when {
            versioned.version == expectedVersion -> false
            versioned.version > expectedVersion -> true
            else ->
                throw Exception(
                    "Expected version $expectedVersion, but found ${versioned.version}."
                )
        }
    }
}
