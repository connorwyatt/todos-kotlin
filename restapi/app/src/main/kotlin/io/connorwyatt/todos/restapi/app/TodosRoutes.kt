package io.connorwyatt.todos.restapi.app

import io.connorwyatt.todos.restapi.app.models.TodoDefinitionRequest
import io.connorwyatt.todos.restapi.app.models.TodoPatchRequest
import io.connorwyatt.todos.restapi.models.TodoReference
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.kodein.di.*
import org.kodein.di.ktor.*

fun Routing.addTodosRoutes() {
    route("/todos") {
        get {
            val service by call.closestDI().instance<TodosService>()

            call.respond(HttpStatusCode.OK, service.retrieveTodos())
        }

        post {
            val service by call.closestDI().instance<TodosService>()

            val request = call.receive<TodoDefinitionRequest>()

            val todoId = service.addTodo(request.toModel())

            call.respond(HttpStatusCode.Accepted, TodoReference(todoId))
        }

        route("{todoId}") {
            get {
                val service by call.closestDI().instance<TodosService>()

                val todoId = call.parameters.getOrFail("todoId")

                val todo = service.retrieveTodo(todoId)

                if (todo == null) {
                    call.respond(HttpStatusCode.NotFound)
                    return@get
                }

                call.respond(HttpStatusCode.OK, todo)
            }

            patch {
                val service by call.closestDI().instance<TodosService>()

                val todoId = call.parameters.getOrFail("todoId")

                val todo = service.retrieveTodo(todoId)

                if (todo == null) {
                    call.respond(HttpStatusCode.NotFound)
                    return@patch
                }

                if (todo.isComplete) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@patch
                }

                val todoPatch = call.receive<TodoPatchRequest>()

                service.updateTodo(todoId, todoPatch.toModel())

                call.respond(HttpStatusCode.Accepted)
            }

            post("actions/complete") {
                val service by call.closestDI().instance<TodosService>()

                val todoId = call.parameters.getOrFail("todoId")

                val todo = service.retrieveTodo(todoId)

                if (todo == null) {
                    call.respond(HttpStatusCode.NotFound)
                    return@post
                }

                if (todo.isComplete) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@post
                }

                service.completeTodo(todoId)

                call.respond(HttpStatusCode.Accepted)
            }
        }
    }
}
