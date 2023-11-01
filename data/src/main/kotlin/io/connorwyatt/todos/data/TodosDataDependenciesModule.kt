package io.connorwyatt.todos.data

import io.connorwyatt.common.eventstore.mongodbmodels.CursorDocument
import io.connorwyatt.common.mongodb.configuration.MongoDBConfiguration
import io.connorwyatt.common.mongodb.kodein.bindMongoDBCollection
import io.connorwyatt.todos.data.configuration.DataConfiguration
import io.connorwyatt.todos.data.configuration.RepositoryImplementation
import io.connorwyatt.todos.data.inmemory.InMemoryTodosRepository
import io.connorwyatt.todos.data.mongodb.MongoDBTodosRepository
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
