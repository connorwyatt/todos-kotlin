package io.connorwyatt.todos.data.mongodb.models

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoClient
import io.connorwyatt.todos.common.data.cursors.Cursor
import io.connorwyatt.todos.common.data.mongodb.CursorDocument
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
            .find(Filters.eq(TodoDocument::id.name, todoId))
            .showRecordId(false)
            .singleOrNull()
            ?.toTodo()

    override suspend fun insertTodo(todo: Todo) {
        todosCollection().insertOne(TodoDocument.fromTodo(todo))
    }

    override suspend fun updateTodo(todo: Todo) {
        todosCollection()
            .replaceOne(Filters.eq(TodoDocument::id.name, todo.id), TodoDocument.fromTodo(todo))
    }

    override suspend fun getStreamPosition(subscriptionName: String, streamName: String): Long? =
        cursorsCollection()
            .find(
                Filters.and(
                    Filters.eq(CursorDocument::subscriptionName.name, subscriptionName),
                    Filters.eq(CursorDocument::streamName.name, streamName),
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
                                CursorDocument::subscriptionName.name,
                                cursor.subscriptionName
                            ),
                            Filters.eq(CursorDocument::streamName.name, cursor.streamName),
                        )
                    )
                    .limit(1)
                    .toList()
                    .isNotEmpty()

            val mongoDBCursor = CursorDocument.fromCursor(cursor)

            if (hasCursor) {
                replaceOne(
                    Filters.and(
                        Filters.eq(CursorDocument::subscriptionName.name, cursor.subscriptionName),
                        Filters.eq(CursorDocument::streamName.name, cursor.streamName),
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
            .getCollection<TodoDocument>(collectionName<TodoDocument>())

    private fun cursorsCollection() =
        mongoClient
            .getDatabase(databaseName)
            .getCollection<CursorDocument>(collectionName<CursorDocument>())
}
