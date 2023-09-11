package io.connorwyatt.todos.restapi.app

import io.connorwyatt.todos.common.domain.aggregates.AggregatesRepository
import io.connorwyatt.todos.common.messaging.commands.commandhandlers.CommandHandler
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
