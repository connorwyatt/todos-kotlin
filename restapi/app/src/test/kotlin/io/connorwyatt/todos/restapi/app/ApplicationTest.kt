package io.connorwyatt.todos.restapi.app

import io.connorwyatt.todos.common.time.clock.Clock
import io.connorwyatt.todos.common.time.clock.testing.FakeClock
import io.connorwyatt.todos.restapi.client.HttpTodosClient
import io.connorwyatt.todos.restapi.models.Todo
import io.connorwyatt.todos.restapi.models.TodoDefinition
import io.ktor.http.*
import java.time.Duration
import kotlin.test.Test
import org.kodein.di.*
import strikt.api.expectThat
import strikt.assertions.*

class ApplicationTest {
    @Test
    fun `when a todo is added, then it can be retrieved`() = testApplicationFixture {
        val client = applicationTestBuilder.createJsonClient()
        val todosClient = HttpTodosClient(client)

        val clock by di.instance<Clock>()

        val todoId = addTodo(todosClient, TodoDefinition("Clean my room"))

        retrieveTodo(todosClient, todoId).also {
            expectThat(it).isEqualTo(Todo(todoId, "Clean my room", clock.now(), false, null))
        }
    }

    @Test
    fun `when a todo is completed, then it is marked as completed`() = testApplicationFixture {
        val client = applicationTestBuilder.createJsonClient()
        val todosClient = HttpTodosClient(client)

        val clock by di.instance<Clock>()
        val fakeClock = clock as FakeClock

        val initialInstant = clock.now()

        val todoId = addTodo(todosClient, TodoDefinition("Clean my room"))

        fakeClock.advanceBy(Duration.ofMinutes(10))

        completeTodo(todosClient, todoId)

        retrieveTodo(todosClient, todoId).also {
            expectThat(it)
                .isEqualTo(Todo(todoId, "Clean my room", initialInstant, true, clock.now()))
        }
    }

    private suspend fun retrieveTodo(client: HttpTodosClient, todoId: String): Todo =
        client
            .retrieveTodo(todoId)
            .also { expectThat(it.status).isEqualTo(HttpStatusCode.OK) }
            .body()

    private suspend fun addTodo(client: HttpTodosClient, definition: TodoDefinition): String =
        client
            .addTodo(definition)
            .also { expectThat(it.status).isEqualTo(HttpStatusCode.Created) }
            .body()
            .id

    private suspend fun completeTodo(client: HttpTodosClient, todoId: String) {
        client.completeTodo(todoId).also { expectThat(it.status).isEqualTo(HttpStatusCode.OK) }
    }
}
