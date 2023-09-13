package io.connorwyatt.todos.common.data.mongodb

import com.mongodb.kotlin.client.coroutine.MongoClient
import org.kodein.di.*

fun mongoDBDependenciesModule(mongoDBConfiguration: MongoDBConfiguration): DI.Module =
    DI.Module(name = ::mongoDBDependenciesModule.name) {
        val connectionString =
            mongoDBConfiguration.connectionString
                ?: throw Exception("MongoDB connection string missing.")

        bindSingleton { MongoClient.create(connectionString) }
        bindSingleton {
            MongoDBInitializer(mongoDBConfiguration.databaseName, instance(), instance())
        }
        bindSet<MongoDBCollectionDefinition>()
    }
