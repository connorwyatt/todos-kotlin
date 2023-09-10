package io.connorwyatt.todos.common.messaging.commands.queues

interface CommandQueueCreator {
    suspend fun createQueues()
}
