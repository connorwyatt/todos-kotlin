package io.connorwyatt.todos.restapi.app

import io.connorwyatt.common.eventstore.aggregates.AggregatesRepository
import io.connorwyatt.common.rabbitmq.commandhandlers.CommandHandler
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
