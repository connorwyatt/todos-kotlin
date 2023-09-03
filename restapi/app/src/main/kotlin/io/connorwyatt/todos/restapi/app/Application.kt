package io.connorwyatt.todos.restapi.app

import io.connorwyatt.todos.common.commonDependenciesModule
import io.connorwyatt.todos.data.todosDataDependenciesModule
import io.connorwyatt.todos.restapi.app.mapping.TodoMapper
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import org.kodein.di.*
import org.kodein.di.ktor.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "localhost") { module(applicationDIConfiguration) }
        .start(wait = true)
}

val applicationDIConfiguration = DI {
    import(commonDependenciesModule)
    import(todosDataDependenciesModule)
    bind<TodosService> { provider { TodosService(instance(), instance(), instance()) } }
    bind<TodoMapper> { provider { TodoMapper() } }
}

fun Application.module(diConfiguration: DI) {
    di { extend(diConfiguration) }
    configureSerialization()
    configureRouting()
}

fun Application.configureSerialization() {
    install(ContentNegotiation) { json() }
}

fun Application.configureRouting() {
    routing { addTodosRoutes() }
}
