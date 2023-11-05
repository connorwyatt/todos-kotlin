package io.connorwyatt.todos.restapi.app

import io.connorwyatt.common.configuration.loadConfigurationFromJsonFiles
import io.connorwyatt.common.server.ApplicationConfiguration
import io.connorwyatt.common.server.Server
import io.connorwyatt.todos.restapi.app.models.TodoDefinitionRequest
import io.connorwyatt.todos.restapi.app.models.TodoPatchRequest
import io.connorwyatt.todos.restapi.app.validation.TodoDefinitionRequestValidator
import io.connorwyatt.todos.restapi.app.validation.TodoPatchRequestValidator
import org.kodein.di.*

fun main() {
    val configuration =
        loadConfigurationFromJsonFiles<Configuration>("configuration", "development")

    Server(
            8080,
            applicationConfiguration(
                    configuration,
                    listOf(applicationDependenciesModule(configuration))
                )
                .build()
        )
        .start()
}

fun applicationConfiguration(
    configuration: Configuration,
    diModules: List<DI.Module>,
) =
    ApplicationConfiguration.Builder().apply {
        addDIModules(diModules)

        addEventStore(configuration.eventStore)
        addMongoDB(configuration.mongoDB)
        addRabbitMQ(configuration.rabbitMQ)
        addHttp()
        addTime()

        configureRequestValidation {
            validate<TodoDefinitionRequest>(TodoDefinitionRequestValidator::validate)
            validate<TodoPatchRequest>(TodoPatchRequestValidator::validate)
        }
        configureRouting { addTodosRoutes() }
    }
