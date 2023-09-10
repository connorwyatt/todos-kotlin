package io.connorwyatt.todos.common.messaging.commands.bus

import io.connorwyatt.todos.common.messaging.commands.CommandEnvelope

interface CommandBus {
    suspend fun send(commandEnvelope: CommandEnvelope)

    suspend fun send(commandEnvelopes: List<CommandEnvelope>)
}
