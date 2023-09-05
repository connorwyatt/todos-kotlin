package io.connorwyatt.todos.restapi.app

import io.connorwyatt.todos.common.commonDependenciesModule
import io.connorwyatt.todos.data.todosDataDependenciesModule
import io.connorwyatt.todos.domain.todosDomainDependenciesModule
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
    embeddedServer(Netty, port = 8080, host = "localhost") { module(applicationDependenciesModule) }
        .start(wait = true)
}

val applicationDependenciesModule = DI {
    import(commonDependenciesModule)
    import(todosDataDependenciesModule)
    import(todosDomainDependenciesModule)
    bindProviderOf(::TodosService)
    bindProviderOf(::TodoMapper)
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
