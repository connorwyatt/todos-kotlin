package io.connorwyatt.todos.data.mongodb.models

import io.connorwyatt.todos.common.data.mongodb.CollectionName
import io.connorwyatt.todos.data.models.Todo
import java.time.Instant
import org.bson.codecs.pojo.annotations.BsonId

@CollectionName("todos")
internal data class MongoDBTodo(
    val id: String,
    val title: String,
    val addedAt: Instant,
    val isComplete: Boolean,
    val completedAt: Instant?,
    val version: Long,
    @BsonId val _id: String = id,
) {
    fun toTodo(): Todo = Todo(id, title, addedAt, isComplete, completedAt, version)

    companion object {
        fun fromTodo(todo: Todo): MongoDBTodo =
            MongoDBTodo(
                todo.id,
                todo.title,
                todo.addedAt,
                todo.isComplete,
                todo.completedAt,
                todo.version
            )
    }
}
