package io.connorwyatt.todos.restapi.app

import io.connorwyatt.todos.common.time.clock.Clock
import io.connorwyatt.todos.restapi.models.Todo
import io.connorwyatt.todos.restapi.models.TodoDefinition
import io.connorwyatt.todos.restapi.models.TodoReference
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import java.util.UUID
import org.kodein.di.*
import org.kodein.di.ktor.*

fun Routing.addTodosRoutes() {
    var todos = mapOf<String, Todo>()

    route("/todos") {
        get { call.respond(HttpStatusCode.OK, todos.values) }

        post {
            val clock by call.closestDI().instance<Clock>()

            val definition = call.receive<TodoDefinition>()

            // TODO: Validation.

            val id = UUID.randomUUID().toString()

            val todo = Todo(id, definition.title, clock.now(), false, null)

            todos = todos.plus(id to todo)

            call.respond(HttpStatusCode.Created, TodoReference(id))
        }

        route("{id}") {
            get {
                val id = call.parameters.getOrFail("id")

                val todo = todos[id]

                if (todo == null) {
                    call.respond(HttpStatusCode.NotFound)
                    return@get
                }

                call.respond(HttpStatusCode.OK, todo)
            }

            post("actions/complete") {
                val clock by call.closestDI().instance<Clock>()

                val id = call.parameters.getOrFail("id")

                val todo = todos[id]

                if (todo == null) {
                    call.respond(HttpStatusCode.NotFound)
                    return@post
                }

                if (todo.isComplete) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@post
                }

                val updatedTodo = todo.copy(isComplete = true, completedAt = clock.now())

                todos = todos.mapValues { if (it.key == id) updatedTodo else it.value }

                call.respond(HttpStatusCode.OK)
            }
        }
    }
}
