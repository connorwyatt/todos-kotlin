package io.connorwyatt.todos.restapi.app

import io.connorwyatt.todos.common.domain.aggregates.AggregatesRepository
import io.connorwyatt.todos.data.TodosRepository
import io.connorwyatt.todos.domain.Todo as TodoAggregate
import io.connorwyatt.todos.restapi.app.mapping.TodoMapper
import io.connorwyatt.todos.restapi.models.Todo
import io.connorwyatt.todos.restapi.models.TodoDefinition
import java.util.*

class TodosService(
    private val aggregatesRepository: AggregatesRepository,
    private val todosRepository: TodosRepository,
    private val mapper: TodoMapper
) {
    suspend fun retrieveTodos(): List<Todo> =
        todosRepository.searchTodos().map(mapper::fromDataModelToRestApiModel)

    suspend fun retrieveTodo(todoId: String): Todo? =
        todosRepository.getTodo(todoId)?.let(mapper::fromDataModelToRestApiModel)

    suspend fun addTodo(definition: TodoDefinition): String {
        val todoId = UUID.randomUUID().toString()

        val aggregate = aggregatesRepository.load<TodoAggregate>(todoId)

        aggregate.addTodo(definition.title)

        aggregatesRepository.save(aggregate)

        return todoId
    }

    suspend fun completeTodo(todoId: String) {
        val aggregate = aggregatesRepository.load<TodoAggregate>(todoId)

        aggregate.completeTodo()

        aggregatesRepository.save(aggregate)
    }
}
