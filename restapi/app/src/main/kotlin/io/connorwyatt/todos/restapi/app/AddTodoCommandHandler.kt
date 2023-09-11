package io.connorwyatt.todos.restapi.app

import io.connorwyatt.todos.common.domain.aggregates.AggregatesRepository
import io.connorwyatt.todos.common.messaging.commands.commandhandlers.CommandHandler
import io.connorwyatt.todos.domain.Todo
import io.connorwyatt.todos.messages.commands.AddTodo

class AddTodoCommandHandler(private val aggregatesRepository: AggregatesRepository) :
    CommandHandler() {
    init {
        handle<AddTodo>(::handle)
    }

    private suspend fun handle(command: AddTodo) {
        val aggregate = aggregatesRepository.load<Todo>(command.id).apply { addTodo(command.title) }

        aggregatesRepository.save(aggregate)
    }
}
