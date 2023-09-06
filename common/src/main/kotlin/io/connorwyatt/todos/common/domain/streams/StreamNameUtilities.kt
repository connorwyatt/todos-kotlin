package io.connorwyatt.todos.common.domain.streams

import io.connorwyatt.todos.common.domain.events.VersionedEventType

object StreamNameUtilities {
    fun streamName(category: String, id: String): String = "$category-$id"

    fun parseStreamName(streamName: String): StreamDescriptor {
        if (streamName.isBlank()) {
            throw Exception("Cannot parse empty/blank streamName.")
        }

        return when {
            streamName == StreamConstants.allStreamName -> StreamDescriptor.All
            streamName.startsWith(StreamConstants.eventTypeStreamNamePrefix) ->
                parseEventTypeStreamName(streamName)
            streamName.startsWith(StreamConstants.categoryStreamNamePrefix) ->
                parseCategoryStreamName(streamName)
            streamName.startsWith('$') ->
                throw Exception("Cannot parse streamName \"$streamName\".")
            else -> parseSingleStreamName(streamName)
        }
    }

    fun parseCategory(streamName: String): String =
        when (val streamDescriptor = parseStreamName(streamName)) {
            is StreamDescriptor.Category -> streamDescriptor.category
            is StreamDescriptor.Single -> streamDescriptor.category
            else -> throw Exception("Cannot parse category from streamName \"$streamName\".")
        }

    private fun parseEventTypeStreamName(streamName: String): StreamDescriptor.EventType {
        val parts = streamName.split('-', limit = 2)

        if (parts.count() != 2) {
            throw Exception("Cannot parse streamName \"$streamName\".")
        }

        val (_, eventType) = parts

        return StreamDescriptor.EventType(VersionedEventType.parse(eventType))
    }

    private fun parseCategoryStreamName(streamName: String): StreamDescriptor.Category {
        val parts = streamName.split('-', limit = 2)

        if (parts.count() != 2) {
            throw Exception("Cannot parse streamName \"$streamName\".")
        }

        val (_, category) = parts

        return StreamDescriptor.Category(category)
    }

    private fun parseSingleStreamName(streamName: String): StreamDescriptor.Single {
        val parts = streamName.split('-', limit = 2)

        if (parts.count() != 2) {
            throw Exception("Cannot parse streamName \"$streamName\".")
        }

        val (category, id) = parts

        return StreamDescriptor.Single(category, id)
    }
}
