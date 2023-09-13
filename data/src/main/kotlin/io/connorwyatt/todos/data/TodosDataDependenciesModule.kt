package io.connorwyatt.todos.data

import io.connorwyatt.todos.data.inmemory.InMemoryTodosRepository
import org.kodein.di.*

fun todosDataDependenciesModule(dataConfiguration: DataConfiguration): DI.Module =
    DI.Module(name = ::todosDataDependenciesModule.name) {
        if (dataConfiguration.useInMemoryRepository) {
            bindSingleton<TodosRepository> { InMemoryTodosRepository() }
        }
    }
