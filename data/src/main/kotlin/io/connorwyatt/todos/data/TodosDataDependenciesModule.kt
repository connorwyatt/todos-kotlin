package io.connorwyatt.todos.data

import io.connorwyatt.todos.common.data.configuration.DataConfiguration
import io.connorwyatt.todos.common.data.configuration.RepositoryImplementation
import io.connorwyatt.todos.common.data.mongodb.CursorDocument
import io.connorwyatt.todos.common.data.mongodb.MongoDBConfiguration
import io.connorwyatt.todos.common.data.mongodb.bindMongoDBCollection
import io.connorwyatt.todos.data.inmemory.InMemoryTodosRepository
import io.connorwyatt.todos.data.mongodb.models.MongoDBTodosRepository
import io.connorwyatt.todos.data.mongodb.models.TodoDocument
import org.kodein.di.*

fun todosDataDependenciesModule(
    dataConfiguration: DataConfiguration,
    mongoDBConfiguration: MongoDBConfiguration,
): DI.Module =
    DI.Module(name = ::todosDataDependenciesModule.name) {
        bindMongoDBCollection<CursorDocument>()
        bindMongoDBCollection<TodoDocument>()

        when (dataConfiguration.repositoryImplementation) {
            RepositoryImplementation.MongoDB -> {
                bindProvider<TodosRepository> {
                    MongoDBTodosRepository(instance(), mongoDBConfiguration.databaseName)
                }
            }
            RepositoryImplementation.InMemory -> {
                bindSingleton<TodosRepository> { InMemoryTodosRepository() }
            }
        }
    }
