package io.connorwyatt.todos.common.data.mongodb

import io.connorwyatt.todos.common.data.cursors.Cursor
import org.bson.codecs.pojo.annotations.BsonId

@CollectionName("cursors")
data class CursorDocument(
    val subscriptionName: String,
    val streamName: String,
    val lastHandledStreamPosition: Long,
    @BsonId val _id: String = primaryKey(subscriptionName, streamName),
) {
    companion object {
        fun fromCursor(todo: Cursor): CursorDocument =
            CursorDocument(
                todo.subscriptionName,
                todo.streamName,
                todo.lastHandledStreamPosition,
            )

        private fun primaryKey(subscriptionName: String, streamName: String) =
            "$subscriptionName::$streamName"
    }
}
