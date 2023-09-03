package io.connorwyatt.todos.common

import io.connorwyatt.todos.common.time.clock.Clock
import io.connorwyatt.todos.common.time.clock.RealClock
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import org.kodein.di.*

const val DEFAULT_JSON_HTTP_CLIENT_TAG = "DEFAULT_JSON_HTTP_CLIENT"

val commonDependenciesModule by
    DI.Module {
        bind<Clock> { provider { new(::RealClock) } }
        bind<HttpClient>(tag = DEFAULT_JSON_HTTP_CLIENT_TAG) { provider { HttpClient(CIO) } }
    }
