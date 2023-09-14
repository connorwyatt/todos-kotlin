package io.connorwyatt.todos.data.mongodb.models

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoClient
import io.connorwyatt.todos.common.data.cursors.Cursor
import io.connorwyatt.todos.common.data.mongodb.MongoDBCursor
import io.connorwyatt.todos.common.data.mongodb.collectionName
import io.connorwyatt.todos.data.TodosRepository
import io.connorwyatt.todos.data.models.Todo
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.flow.toList

class MongoDBTodosRepository(
    private val mongoClient: MongoClient,
    private val databaseName: String
) : TodosRepository {
    override suspend fun searchTodos(): List<Todo> =
        todosCollection().find().map { it.toTodo() }.toList()

    override suspend fun getTodo(todoId: String): Todo? =
        todosCollection()
            .find(Filters.eq(MongoDBTodo::id.name, todoId))
            .showRecordId(false)
            .singleOrNull()
            ?.toTodo()

    override suspend fun insertTodo(todo: Todo) {
        todosCollection().insertOne(MongoDBTodo.fromTodo(todo))
    }

    override suspend fun updateTodo(todo: Todo) {
        todosCollection()
            .replaceOne(Filters.eq(MongoDBTodo::id.name, todo.id), MongoDBTodo.fromTodo(todo))
    }

    override suspend fun getStreamPosition(subscriptionName: String, streamName: String): Long? =
        cursorsCollection()
            .find(
                Filters.and(
                    Filters.eq(MongoDBCursor::subscriptionName.name, subscriptionName),
                    Filters.eq(MongoDBCursor::streamName.name, streamName),
                )
            )
            .singleOrNull()
            ?.lastHandledStreamPosition

    override suspend fun updateStreamPosition(cursor: Cursor) {
        cursorsCollection().apply {
            val hasCursor =
                find(
                        Filters.and(
                            Filters.eq(
                                MongoDBCursor::subscriptionName.name,
                                cursor.subscriptionName
                            ),
                            Filters.eq(MongoDBCursor::streamName.name, cursor.streamName),
                        )
                    )
                    .limit(1)
                    .toList()
                    .isNotEmpty()

            val mongoDBCursor = MongoDBCursor.fromCursor(cursor)

            if (hasCursor) {
                replaceOne(
                    Filters.and(
                        Filters.eq(MongoDBCursor::subscriptionName.name, cursor.subscriptionName),
                        Filters.eq(MongoDBCursor::streamName.name, cursor.streamName),
                    ),
                    mongoDBCursor
                )
            } else {
                insertOne(mongoDBCursor)
            }
        }
    }

    private fun todosCollection() =
        mongoClient
            .getDatabase(databaseName)
            .getCollection<MongoDBTodo>(collectionName<MongoDBTodo>())

    private fun cursorsCollection() =
        mongoClient
            .getDatabase(databaseName)
            .getCollection<MongoDBCursor>(collectionName<MongoDBCursor>())
}
