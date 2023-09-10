package io.connorwyatt.todos.common.messaging.commands.bus

import io.connorwyatt.todos.common.messaging.commands.CommandEnvelope

class NoopCommandBus() : CommandBus {
    override suspend fun send(commandEnvelope: CommandEnvelope) {}

    override suspend fun send(commandEnvelopes: List<CommandEnvelope>) {}
}
