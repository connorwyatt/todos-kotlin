package io.connorwyatt.todos.data

import io.connorwyatt.todos.common.data.configuration.DataConfiguration
import io.connorwyatt.todos.common.data.mongodb.MongoDBConfiguration
import io.connorwyatt.todos.common.data.mongodb.MongoDBCursor
import io.connorwyatt.todos.common.data.mongodb.bindMongoDBCollection
import io.connorwyatt.todos.data.inmemory.InMemoryTodosRepository
import io.connorwyatt.todos.data.mongodb.models.MongoDBTodo
import io.connorwyatt.todos.data.mongodb.models.MongoDBTodosRepository
import org.kodein.di.*

fun todosDataDependenciesModule(
    dataConfiguration: DataConfiguration,
    mongoDBConfiguration: MongoDBConfiguration,
): DI.Module =
    DI.Module(name = ::todosDataDependenciesModule.name) {
        bindMongoDBCollection<MongoDBCursor>()
        bindMongoDBCollection<MongoDBTodo>()

        if (!dataConfiguration.useInMemoryRepository) {
            bindProvider<TodosRepository> {
                MongoDBTodosRepository(instance(), mongoDBConfiguration.databaseName)
            }
        } else {
            bindSingleton<TodosRepository> { InMemoryTodosRepository() }
        }
    }
