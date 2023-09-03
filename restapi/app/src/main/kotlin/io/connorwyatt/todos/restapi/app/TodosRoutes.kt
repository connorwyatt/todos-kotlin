package io.connorwyatt.todos.restapi.app

import io.connorwyatt.todos.restapi.models.TodoDefinition
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

            val definition = call.receive<TodoDefinition>()

            // TODO: Validation.

            val todoId = service.addTodo(definition)

            call.respond(HttpStatusCode.Created, TodoReference(todoId))
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

                call.respond(HttpStatusCode.OK)
            }
        }
    }
}
