package io.connorwyatt.todos.restapi.client

import io.connorwyatt.common.http.DEFAULT_JSON_HTTP_CLIENT_TAG
import org.kodein.di.*

val todosClientDependenciesModule by
    DI.Module {
        bind<TodosClient> {
            provider { HttpTodosClient(instance(tag = DEFAULT_JSON_HTTP_CLIENT_TAG)) }
        }
    }
