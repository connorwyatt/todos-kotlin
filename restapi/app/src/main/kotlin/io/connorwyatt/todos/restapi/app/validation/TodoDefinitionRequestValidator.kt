package io.connorwyatt.todos.restapi.app.validation

import io.connorwyatt.todos.restapi.app.models.TodoDefinitionRequest
import io.ktor.server.plugins.requestvalidation.*

object TodoDefinitionRequestValidator {
    fun validate(todoDefinitionRequest: TodoDefinitionRequest): ValidationResult {
        var errors = listOf<String>()

        if (todoDefinitionRequest.title.isNullOrEmpty()) {
            errors = errors.plus("'${TodoDefinitionRequest::title.name}' must not be empty.")
        } else {
            when {
                todoDefinitionRequest.title.length < 3 ->
                    errors =
                        errors.plus(
                            "'${TodoDefinitionRequest::title.name}' must be a minimum of 3 characters."
                        )
                todoDefinitionRequest.title.length > 100 ->
                    errors =
                        errors.plus(
                            "'${TodoDefinitionRequest::title.name}' must be a maximum of 100 characters."
                        )
            }
        }

        return if (errors.isEmpty()) ValidationResult.Valid else ValidationResult.Invalid(errors)
    }
}
