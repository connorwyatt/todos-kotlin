package io.connorwyatt.todos.restapi.app

import io.connorwyatt.todos.common.messaging.commands.CommandEnvelope
import io.connorwyatt.todos.common.messaging.commands.bus.CommandBus
import io.connorwyatt.todos.data.TodosRepository
import io.connorwyatt.todos.messages.commands.AddTodo
import io.connorwyatt.todos.messages.commands.CompleteTodo
import io.connorwyatt.todos.messages.commands.UpdateTodo
import io.connorwyatt.todos.restapi.app.mapping.TodoMapper
import io.connorwyatt.todos.restapi.models.Todo
import io.connorwyatt.todos.restapi.models.TodoDefinition
import io.connorwyatt.todos.restapi.models.TodoPatch
import java.util.*

class TodosService(
    private val todosRepository: TodosRepository,
    private val mapper: TodoMapper,
    private val commandBus: CommandBus
) {
    suspend fun retrieveTodos(): List<Todo> =
        todosRepository.searchTodos().map(mapper::fromDataModelToRestApiModel)

    suspend fun retrieveTodo(todoId: String): Todo? =
        todosRepository.getTodo(todoId)?.let(mapper::fromDataModelToRestApiModel)

    suspend fun addTodo(definition: TodoDefinition): String {
        val todoId = UUID.randomUUID().toString()

        commandBus.send(CommandEnvelope(AddTodo(todoId, definition.title)))

        return todoId
    }

    suspend fun updateTodo(todoId: String, todoPatch: TodoPatch) {
        commandBus.send(CommandEnvelope(UpdateTodo(todoId, todoPatch.title)))
    }

    suspend fun completeTodo(todoId: String) {
        commandBus.send(CommandEnvelope(CompleteTodo(todoId)))
    }
}
