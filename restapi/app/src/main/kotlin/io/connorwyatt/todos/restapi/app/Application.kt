package io.connorwyatt.todos.restapi.app

import io.connorwyatt.common.configuration.loadConfigurationFromJsonFiles
import io.connorwyatt.common.eventstore.ktor.configureEventStore
import io.connorwyatt.common.http.validation.ValidationProblemResponse
import io.connorwyatt.common.mongodb.ktor.configureMongoDB
import io.connorwyatt.common.rabbitmq.kodein.bindCommandHandler
import io.connorwyatt.common.rabbitmq.kodein.bindCommandQueueDefinition
import io.connorwyatt.common.rabbitmq.kodein.bindCommandRoutingRules
import io.connorwyatt.common.rabbitmq.ktor.configureRabbitMQ
import io.connorwyatt.todos.common.commonDependenciesModule
import io.connorwyatt.todos.data.todosDataDependenciesModule
import io.connorwyatt.todos.domain.todosDomainDependenciesModule
import io.connorwyatt.todos.messages.commands.AddTodo
import io.connorwyatt.todos.messages.commands.CompleteTodo
import io.connorwyatt.todos.messages.commands.UpdateTodo
import io.connorwyatt.todos.messages.commands.todosMessagesCommandsDependenciesModule
import io.connorwyatt.todos.projector.todosProjectorDependenciesModule
import io.connorwyatt.todos.restapi.app.mapping.TodoMapper
import io.connorwyatt.todos.restapi.app.models.TodoDefinitionRequest
import io.connorwyatt.todos.restapi.app.models.TodoPatchRequest
import io.connorwyatt.todos.restapi.app.validation.TodoDefinitionRequestValidator
import io.connorwyatt.todos.restapi.app.validation.TodoPatchRequestValidator
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callid.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*
import kotlinx.coroutines.runBlocking
import org.kodein.di.*
import org.kodein.di.ktor.*

fun applicationDependenciesModule(configuration: Configuration): DI.Module =
    DI.Module(name = ::applicationDependenciesModule.name) {
        import(
            commonDependenciesModule(
                configuration.eventStore,
                configuration.mongoDB,
                configuration.rabbitMQ,
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
        bindCommandHandler<UpdateTodo> { new(::UpdateTodoCommandHandler) }
        bindCommandHandler<CompleteTodo> { new(::CompleteTodoCommandHandler) }
    }

fun main() {
    val configuration =
        loadConfigurationFromJsonFiles<Configuration>("configuration", "development")

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
    configureRequestValidation()
    configureStatusPages()
    configureCallId()
    configureCallLogging()
    configureRouting()
}

private fun Application.configureSerialization() {
    install(ContentNegotiation) { json() }
}

private fun Application.configureRequestValidation() {
    install(RequestValidation) {
        validate<TodoDefinitionRequest>(TodoDefinitionRequestValidator::validate)
        validate<TodoPatchRequest>(TodoPatchRequestValidator::validate)
    }
}

private fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<RequestValidationException> { call, cause ->
            call.response.headers.append(
                HttpHeaders.ContentType,
                ContentType.Application.ProblemJson.toString()
            )
            call.respond(HttpStatusCode.BadRequest, ValidationProblemResponse(cause.reasons))
        }
        exception<Throwable> { call, _ ->
            call.respondText("", ContentType.Any, status = HttpStatusCode.InternalServerError)
        }
    }
}

private fun Application.configureCallId() {
    install(CallId) {
        generate { UUID.randomUUID().toString() }
        replyToHeader(HttpHeaders.XRequestId)
    }
}

private fun Application.configureCallLogging() {
    install(CallLogging) {
        callIdMdc("request-id")
        disableDefaultColors()
        mdc("http-method") { call -> call.request.httpMethod.value }
        mdc("request-url") { call -> call.request.uri }
        mdc("status-code") { call -> call.response.status()?.value?.toString() }
    }
}

private fun Application.configureRouting() {
    routing { addTodosRoutes() }
}
