package io.connorwyatt.todos.data

import org.kodein.di.*

val todosDataDependenciesModule by
    DI.Module { bind<TodosRepository> { singleton { InMemoryTodosRepository() } } }
