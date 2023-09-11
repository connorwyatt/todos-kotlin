package io.connorwyatt.todos.common.messaging

import com.rabbitmq.client.ConnectionFactory
import io.connorwyatt.todos.common.messaging.commands.CommandMap
import io.connorwyatt.todos.common.messaging.commands.CommandMapDefinition
import io.connorwyatt.todos.common.messaging.commands.bus.CommandBus
import io.connorwyatt.todos.common.messaging.commands.bus.InMemoryCommandBus
import io.connorwyatt.todos.common.messaging.commands.bus.RabbitMQCommandBus
import io.connorwyatt.todos.common.messaging.commands.commandhandlers.CommandHandlerDefinition
import io.connorwyatt.todos.common.messaging.commands.commandhandlers.CommandHandlerMap
import io.connorwyatt.todos.common.messaging.commands.commandhandlers.CommandHandlerRouter
import io.connorwyatt.todos.common.messaging.commands.commandhandlers.RabbitMQSubscriptionsManager
import io.connorwyatt.todos.common.messaging.commands.queues.*
import org.kodein.di.*

fun messagingDependenciesModule(rabbitMQConfiguration: RabbitMQConfiguration): DI.Module =
    DI.Module(name = ::messagingDependenciesModule.name) {
        if (!rabbitMQConfiguration.useInMemoryRabbitMQ) {
            val connectionString =
                (rabbitMQConfiguration.connectionString
                    ?: throw Exception("Connection string not set for RabbitMQ."))

            val exchangeName =
                (rabbitMQConfiguration.exchangeName
                    ?: throw Exception("Exchange name not set for RabbitMQ."))

            bindSingleton { ConnectionFactory().apply { setUri(connectionString) } }
            bindSingleton { instance<ConnectionFactory>().newConnection() }

            bindProvider<CommandQueueCreator> {
                RabbitMQCommandQueueCreator(exchangeName, instance(), instance())
            }

            bindProvider<CommandBus> {
                RabbitMQCommandBus(instance(), exchangeName, instance(), instance())
            }

            bindSingleton {
                RabbitMQSubscriptionsManager(
                    rabbitMQConfiguration.exchangeName,
                    instance(),
                    instance(),
                    instance(),
                    instance(),
                )
            }
        } else {
            bindProvider<CommandQueueCreator> { new(::NoopCommandQueueCreator) }
            bindSingleton<CommandBus> { new(::InMemoryCommandBus) }
        }

        bindSingletonOf(::CommandQueueList)
        bindSet<CommandQueueDefinition>()

        bindSingletonOf(::CommandMap)
        bindSet<CommandMapDefinition>()

        bindSingletonOf(::CommandHandlerMap)
        bindSet<CommandHandlerDefinition>()

        bindSingleton {
            CommandHandlerRouter { clazz ->
                val creator = instance<CommandHandlerMap>().creatorFor(clazz)

                newInstance { creator(this) }
            }
        }
    }
