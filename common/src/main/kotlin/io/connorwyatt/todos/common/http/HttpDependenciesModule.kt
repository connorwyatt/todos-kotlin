package io.connorwyatt.todos.common.http

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import org.kodein.di.*

const val DEFAULT_JSON_HTTP_CLIENT_TAG = "DEFAULT_JSON_HTTP_CLIENT"

val httpDependenciesModule by
    DI.Module {
        bind<HttpClient>(tag = DEFAULT_JSON_HTTP_CLIENT_TAG) { provider { HttpClient(CIO) } }
    }
