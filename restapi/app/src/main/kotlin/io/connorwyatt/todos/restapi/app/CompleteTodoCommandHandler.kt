package io.connorwyatt.todos.restapi.app

import io.connorwyatt.common.eventstore.aggregates.AggregatesRepository
import io.connorwyatt.common.rabbitmq.commandhandlers.CommandHandler
import io.connorwyatt.todos.domain.Todo
import io.connorwyatt.todos.messages.commands.CompleteTodo

class CompleteTodoCommandHandler(private val aggregatesRepository: AggregatesRepository) :
    CommandHandler() {
    init {
        handle<CompleteTodo>(::handle)
    }

    private suspend fun handle(command: CompleteTodo) {
        val aggregate = aggregatesRepository.load<Todo>(command.id).apply { completeTodo() }

        aggregatesRepository.save(aggregate)
    }
}
