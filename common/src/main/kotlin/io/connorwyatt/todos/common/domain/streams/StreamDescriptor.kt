package io.connorwyatt.todos.common.domain.streams

import io.connorwyatt.todos.common.domain.events.VersionedEventType

interface StreamDescriptor {
    val streamName: String

    data object All : StreamDescriptor {
        override val streamName: String
            get() = StreamConstants.allStreamName
    }

    data class EventType(val versionedEventType: VersionedEventType) : StreamDescriptor {
        override val streamName: String
            get() = "${StreamConstants.eventTypeStreamNamePrefix}-$versionedEventType"
    }

    data class Category(val category: String) : StreamDescriptor {
        override val streamName: String
            get() = "${StreamConstants.categoryStreamNamePrefix}-$category"
    }

    data class Single(val category: String, val id: String) : StreamDescriptor {
        override val streamName: String
            get() = "$category-$id"
    }
}
