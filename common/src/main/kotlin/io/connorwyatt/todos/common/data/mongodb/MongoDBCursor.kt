package io.connorwyatt.todos.common.data.mongodb

import io.connorwyatt.todos.common.data.cursors.Cursor
import org.bson.codecs.pojo.annotations.BsonId

@CollectionName("cursors")
data class MongoDBCursor(
    val subscriptionName: String,
    val streamName: String,
    val lastHandledStreamPosition: Long,
    @BsonId val _id: String = primaryKey(subscriptionName, streamName),
) {
    companion object {
        fun fromCursor(todo: Cursor): MongoDBCursor =
            MongoDBCursor(
                todo.subscriptionName,
                todo.streamName,
                todo.lastHandledStreamPosition,
            )

        private fun primaryKey(subscriptionName: String, streamName: String) =
            "$subscriptionName::$streamName"
    }
}
