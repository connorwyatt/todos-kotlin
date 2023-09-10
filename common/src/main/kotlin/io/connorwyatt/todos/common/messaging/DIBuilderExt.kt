package io.connorwyatt.todos.common.messaging

import io.connorwyatt.todos.common.messaging.commands.Command
import io.connorwyatt.todos.common.messaging.commands.CommandMapDefinition
import io.connorwyatt.todos.common.messaging.commands.commandhandlers.CommandHandler
import io.connorwyatt.todos.common.messaging.commands.commandhandlers.CommandHandlerDefinition
import io.connorwyatt.todos.common.messaging.commands.queues.CommandQueueDefinition
import io.connorwyatt.todos.common.messaging.commands.routing.CommandRoutingRulesBuilder
import org.kodein.di.*

fun DI.Builder.bindCommandQueueDefinition(queueName: String) {
    inBindSet<CommandQueueDefinition> { add { singleton { CommandQueueDefinition(queueName) } } }
}

inline fun <reified TCommand : Command> DI.Builder.bindCommandDefinition(type: String) {
    inBindSet<CommandMapDefinition> {
        add { singleton { CommandMapDefinition(type, TCommand::class) } }
    }
}

inline fun <reified TCommand : Command> DI.Builder.bindCommandHandler(
    noinline constructor: DirectDI.() -> CommandHandler
) {
    inBindSet<CommandHandlerDefinition> {
        add { singleton { CommandHandlerDefinition(TCommand::class, constructor) } }
    }
}

fun DI.Builder.bindCommandRoutingRules(function: CommandRoutingRulesBuilder.() -> Unit) {
    val builder = CommandRoutingRulesBuilder()

    function(builder)

    bindSingleton { builder.build() }
}
