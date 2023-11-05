package io.connorwyatt.todos.restapi.app

import io.connorwyatt.common.rabbitmq.kodein.bindCommandHandler
import io.connorwyatt.common.rabbitmq.kodein.bindCommandQueueDefinition
import io.connorwyatt.common.rabbitmq.kodein.bindCommandRoutingRules
import io.connorwyatt.todos.data.todosDataDependenciesModule
import io.connorwyatt.todos.domain.todosDomainDependenciesModule
import io.connorwyatt.todos.messages.commands.AddTodo
import io.connorwyatt.todos.messages.commands.CompleteTodo
import io.connorwyatt.todos.messages.commands.UpdateTodo
import io.connorwyatt.todos.messages.commands.todosMessagesCommandsDependenciesModule
import io.connorwyatt.todos.projector.todosProjectorDependenciesModule
import io.connorwyatt.todos.restapi.app.mapping.TodoMapper
import org.kodein.di.*

fun applicationDependenciesModule(configuration: Configuration): DI.Module =
    DI.Module(name = ::applicationDependenciesModule.name) {
        import(todosDataDependenciesModule(configuration.data, configuration.mongoDB))
        import(todosDomainDependenciesModule)
        import(todosMessagesCommandsDependenciesModule)
        import(todosProjectorDependenciesModule)

        bindProviderOf(::TodosService)
        bindProviderOf(::TodoMapper)
        bindCommandQueueDefinition("commands")
        bindCommandRoutingRules { defaultQueue("commands") }
        bindCommandHandler<AddTodo> { new(::AddTodoCommandHandler) }
        bindCommandHandler<UpdateTodo> { new(::UpdateTodoCommandHandler) }
        bindCommandHandler<CompleteTodo> { new(::CompleteTodoCommandHandler) }
    }
