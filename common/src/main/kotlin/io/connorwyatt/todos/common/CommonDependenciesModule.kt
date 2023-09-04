package io.connorwyatt.todos.common

import io.connorwyatt.todos.common.http.httpDependenciesModule
import io.connorwyatt.todos.common.time.timeDependenciesModule
import org.kodein.di.*

val commonDependenciesModule by
    DI.Module {
        import(httpDependenciesModule)
        import(timeDependenciesModule)
    }
