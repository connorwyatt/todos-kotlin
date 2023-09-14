package io.connorwyatt.todos.common.data.mongodb

data class MongoDBConfiguration(
    val connectionString: String?,
    val databaseName: String,
)
