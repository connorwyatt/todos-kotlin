package io.connorwyatt.todos.common.http

import io.ktor.client.statement.*

open class BaseHttpResult(private val response: HttpResponse) {
    val status = response.status
}
