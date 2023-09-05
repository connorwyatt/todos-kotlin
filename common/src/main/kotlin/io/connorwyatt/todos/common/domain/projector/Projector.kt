package io.connorwyatt.todos.common.domain.projector

import io.connorwyatt.todos.common.domain.eventhandlers.EventHandler
import io.connorwyatt.todos.common.domain.events.EventMetadata
import io.connorwyatt.todos.common.models.Versioned

abstract class Projector : EventHandler() {
    protected fun isEventHandled(versioned: Versioned, metadata: EventMetadata): Boolean {
        val expectedVersion = metadata.streamPosition - 1

        return when {
            versioned.version == expectedVersion -> false
            versioned.version < expectedVersion -> true
            else ->
                throw Exception(
                    "Expected version $expectedVersion, but found ${versioned.version}."
                )
        }
    }
}
