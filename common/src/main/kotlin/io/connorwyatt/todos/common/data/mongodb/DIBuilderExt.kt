package io.connorwyatt.todos.common.data.mongodb

import org.kodein.di.*

inline fun <reified T> DI.Builder.bindMongoDBCollection() {
    inBindSet<MongoDBCollectionDefinition> {
        add { singleton { MongoDBCollectionDefinition(collectionName<T>()) } }
    }
}
