package io.connorwyatt.todos.restapi.app

import io.connorwyatt.todos.common.domain.aggregates.AggregatesRepository
import io.connorwyatt.todos.common.messaging.commands.commandhandlers.CommandHandler
import io.connorwyatt.todos.domain.Todo
import io.connorwyatt.todos.messages.commands.UpdateTodo

class UpdateTodoCommandHandler(private val aggregatesRepository: AggregatesRepository) :
    CommandHandler() {
    init {
        handle<UpdateTodo>(::handle)
    }

    private suspend fun handle(command: UpdateTodo) {
        val aggregate =
            aggregatesRepository.load<Todo>(command.id).apply { updateTodo(command.title) }

        aggregatesRepository.save(aggregate)
    }
}
