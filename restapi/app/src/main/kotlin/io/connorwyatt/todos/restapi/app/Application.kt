package io.connorwyatt.todos.restapi.app

import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.addResourceSource
import io.connorwyatt.todos.common.commonDependenciesModule
import io.connorwyatt.todos.common.configureEventStore
import io.connorwyatt.todos.common.configureMongoDB
import io.connorwyatt.todos.common.configureRabbitMQ
import io.connorwyatt.todos.common.messaging.bindCommandHandler
import io.connorwyatt.todos.common.messaging.bindCommandQueueDefinition
import io.connorwyatt.todos.common.messaging.bindCommandRoutingRules
import io.connorwyatt.todos.data.todosDataDependenciesModule
import io.connorwyatt.todos.domain.todosDomainDependenciesModule
import io.connorwyatt.todos.messages.commands.AddTodo
import io.connorwyatt.todos.messages.commands.CompleteTodo
import io.connorwyatt.todos.messages.commands.todosMessagesCommandsDependenciesModule
import io.connorwyatt.todos.projector.todosProjectorDependenciesModule
import io.connorwyatt.todos.restapi.app.mapping.TodoMapper
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import kotlinx.coroutines.runBlocking
import org.kodein.di.*
import org.kodein.di.ktor.*

fun applicationDependenciesModule(configuration: Configuration): DI.Module =
    DI.Module(name = ::applicationDependenciesModule.name) {
        import(
            commonDependenciesModule(
                configuration.data,
                configuration.eventStore,
                configuration.mongoDB,
                configuration.rabbitMQ
            )
        )
        import(todosDataDependenciesModule(configuration.data, configuration.mongoDB))
        import(todosDomainDependenciesModule)
        import(todosMessagesCommandsDependenciesModule)
        import(todosProjectorDependenciesModule)
        bindProviderOf(::TodosService)
        bindProviderOf(::TodoMapper)
        bindCommandQueueDefinition("commands")
        bindCommandRoutingRules { defaultQueue("commands") }
        bindCommandHandler<AddTodo> { new(::AddTodoCommandHandler) }
        bindCommandHandler<CompleteTodo> { new(::CompleteTodoCommandHandler) }
    }

fun main() {
    val configuration = buildConfiguration()

    embeddedServer(Netty, port = 8080, host = "localhost") {
            runBlocking {
                module(configuration, DI { import(applicationDependenciesModule(configuration)) })
            }
        }
        .start(wait = true)
}

suspend fun Application.module(configuration: Configuration, diConfiguration: DI) {
    di { extend(diConfiguration) }
    configureMongoDB()
    configureEventStore(configuration.eventStore)
    configureRabbitMQ(configuration.rabbitMQ)
    configureSerialization()
    configureRouting()
}

private fun buildConfiguration(): Configuration =
    ConfigLoaderBuilder.default()
        .apply {
            val environment = "development"

            addResourceSource("/configuration.$environment.json", optional = true)
            addResourceSource("/configuration.json", optional = true)
        }
        .build()
        .loadConfigOrThrow<Configuration>()

private fun Application.configureSerialization() {
    install(ContentNegotiation) { json() }
}

private fun Application.configureRouting() {
    routing { addTodosRoutes() }
}
