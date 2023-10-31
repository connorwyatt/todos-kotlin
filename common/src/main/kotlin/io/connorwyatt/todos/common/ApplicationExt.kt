package io.connorwyatt.todos.common

import io.connorwyatt.todos.common.data.mongodb.MongoDBInitializer
import io.ktor.server.application.*
import org.kodein.di.*
import org.kodein.di.ktor.*

suspend fun Application.configureMongoDB() {
    val mongoDBInitializer by closestDI().instanceOrNull<MongoDBInitializer>()

    mongoDBInitializer?.initialize()
}
