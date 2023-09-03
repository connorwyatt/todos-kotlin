package io.connorwyatt.todos.restapi.app.mapping

import io.connorwyatt.todos.data.models.Todo as DataTodo
import io.connorwyatt.todos.restapi.models.Todo as RestApiTodo

class TodoMapper {
    fun fromDataModelToRestApiModel(todo: DataTodo): RestApiTodo =
        RestApiTodo(todo.id, todo.title, todo.addedAt, todo.isComplete, todo.completedAt)
}
