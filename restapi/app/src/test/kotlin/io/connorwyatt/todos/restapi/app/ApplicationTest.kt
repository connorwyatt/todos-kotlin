package io.connorwyatt.todos.restapi.app

import io.connorwyatt.common.optional.Optional.Present
import io.connorwyatt.common.time.clock.Clock
import io.connorwyatt.common.time.clock.testing.FakeClock
import io.connorwyatt.todos.restapi.client.HttpTodosClient
import io.connorwyatt.todos.restapi.models.Todo
import io.connorwyatt.todos.restapi.models.TodoDefinition
import io.connorwyatt.todos.restapi.models.TodoPatch
import io.ktor.http.*
import java.time.Duration
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.kodein.di.*
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ApplicationTest {
    @Test
    fun `when a todo is added, then it can be retrieved`() = runBlocking {
        testApplicationFixture {
            val client = applicationTestBuilder.createJsonClient()
            val todosClient = HttpTodosClient(client)

            val clock by di.instance<Clock>()

            val todoId = addTodo(todosClient, TodoDefinition("Clean my room"))

            waitForConsistency()

            retrieveTodo(todosClient, todoId).also {
                expectThat(it).isEqualTo(Todo(todoId, "Clean my room", clock.now(), false, null))
            }
        }
    }

    @Test
    fun `when a todo is updated, then it is updated`() = runBlocking {
        testApplicationFixture {
            val client = applicationTestBuilder.createJsonClient()
            val todosClient = HttpTodosClient(client)

            val todoId = addTodo(todosClient, TodoDefinition("Cleen my room"))

            waitForConsistency()

            updateTodo(todosClient, todoId, TodoPatch(Present("Clean my room")))

            waitForConsistency()

            retrieveTodo(todosClient, todoId).also {
                expectThat(it.title).isEqualTo("Clean my room")
            }
        }
    }

    @Test
    fun `when a todo is completed, then it is marked as completed`() = runBlocking {
        testApplicationFixture {
            val client = applicationTestBuilder.createJsonClient()
            val todosClient = HttpTodosClient(client)

            val clock by di.instance<Clock>()
            val fakeClock = clock as FakeClock

            val initialInstant = clock.now()

            val todoId = addTodo(todosClient, TodoDefinition("Clean my room"))

            waitForConsistency()

            fakeClock.advanceBy(Duration.ofMinutes(10))

            completeTodo(todosClient, todoId)

            waitForConsistency()

            retrieveTodo(todosClient, todoId).also {
                expectThat(it)
                    .isEqualTo(Todo(todoId, "Clean my room", initialInstant, true, clock.now()))
            }
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
            .also { expectThat(it.status).isEqualTo(HttpStatusCode.Accepted) }
            .body()
            .id

    private suspend fun updateTodo(client: HttpTodosClient, todoId: String, patch: TodoPatch) =
        client.updateTodo(todoId, patch).also {
            expectThat(it.status).isEqualTo(HttpStatusCode.Accepted)
        }

    private suspend fun completeTodo(client: HttpTodosClient, todoId: String) {
        client.completeTodo(todoId).also {
            expectThat(it.status).isEqualTo(HttpStatusCode.Accepted)
        }
    }
}
