package io.connorwyatt.todos.common.messaging.commands.queues

class NoopCommandQueueCreator() : CommandQueueCreator {
    override suspend fun createQueues() {}
}
