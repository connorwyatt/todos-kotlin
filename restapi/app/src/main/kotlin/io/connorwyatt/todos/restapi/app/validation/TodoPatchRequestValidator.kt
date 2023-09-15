package io.connorwyatt.todos.restapi.app.validation

import io.connorwyatt.todos.restapi.app.models.TodoPatchRequest
import io.ktor.server.plugins.requestvalidation.*

object TodoPatchRequestValidator {
    fun validate(todoPatchRequest: TodoPatchRequest): ValidationResult {
        var errors = listOf<String>()

        todoPatchRequest.title.ifPresent { title ->
            if (title.isNullOrEmpty()) {
                errors = errors.plus("'${TodoPatchRequest::title.name}' must not be empty.")
            } else {
                when {
                    title.length < 3 ->
                        errors =
                            errors.plus(
                                "'${TodoPatchRequest::title.name}' must be a minimum of 3 characters."
                            )
                    title.length > 100 ->
                        errors =
                            errors.plus(
                                "'${TodoPatchRequest::title.name}' must be a maximum of 100 characters."
                            )
                }
            }
        }

        return if (errors.isEmpty()) ValidationResult.Valid else ValidationResult.Invalid(errors)
    }
}
