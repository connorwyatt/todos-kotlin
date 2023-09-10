package io.connorwyatt.todos.common.messaging

import io.connorwyatt.todos.common.messaging.commands.queues.CommandQueueDefinition
import org.kodein.di.*

fun DI.Builder.bindCommandQueueDefinition(queueName: String) {
    inBindSet<CommandQueueDefinition> { add { singleton { CommandQueueDefinition(queueName) } } }
}
