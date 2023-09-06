package io.connorwyatt.todos.restapi.app

import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.addResourceSource
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

fun applicationDependenciesModule(configuration: Configuration) = DI {
    import(commonDependenciesModule(configuration.eventStore))
    import(todosDataDependenciesModule)
    import(todosDomainDependenciesModule)
    bindProviderOf(::TodosService)
    bindProviderOf(::TodoMapper)
}

fun main() {
    val configuration = buildConfiguration()

    embeddedServer(Netty, port = 8080, host = "localhost") {
            module(applicationDependenciesModule(configuration))
        }
        .start(wait = true)
}

fun Application.module(diConfiguration: DI) {
    di { extend(diConfiguration) }
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
