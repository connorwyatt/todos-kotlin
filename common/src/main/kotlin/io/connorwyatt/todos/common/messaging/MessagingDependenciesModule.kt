package io.connorwyatt.todos.common.messaging

import com.rabbitmq.client.ConnectionFactory
import io.connorwyatt.todos.common.messaging.commands.queues.*
import org.kodein.di.*

fun messagingDependenciesModule(rabbitMQConfiguration: RabbitMQConfiguration): DI.Module =
    DI.Module(name = ::messagingDependenciesModule.name) {
        if (!rabbitMQConfiguration.useInMemoryRabbitMQ) {
            bindSingleton {
                ConnectionFactory().apply { setUri(rabbitMQConfiguration.connectionString) }
            }
            bindSingleton { instance<ConnectionFactory>().newConnection() }

            bindProvider<CommandQueueCreator> {
                RabbitMQCommandQueueCreator(
                    rabbitMQConfiguration.exchangeName
                        ?: throw Exception("Connection string not set for RabbitMQ."),
                    instance(),
                    instance()
                )
            }
        } else {
            bindProvider<CommandQueueCreator> { new(::NoopCommandQueueCreator) }
        }

        bindSingletonOf(::CommandQueueList)
        bindSet<CommandQueueDefinition>()
    }
