package io.connorwyatt.todos.restapi.app

import io.connorwyatt.common.eventstore.configuration.EventStoreConfiguration
import io.connorwyatt.common.eventstore.events.EventsRepository
import io.connorwyatt.common.eventstore.events.InMemoryEventsRepository
import io.connorwyatt.common.mongodb.configuration.MongoDBConfiguration
import io.connorwyatt.common.rabbitmq.bus.CommandBus
import io.connorwyatt.common.rabbitmq.bus.InMemoryCommandBus
import io.connorwyatt.common.rabbitmq.configuration.RabbitMQConfiguration
import io.connorwyatt.common.server.ApplicationConfiguration
import io.connorwyatt.common.time.TimeUtilities
import io.connorwyatt.common.time.clock.Clock
import io.connorwyatt.common.time.clock.testing.FakeClock
import io.connorwyatt.todos.data.configuration.DataConfiguration
import io.connorwyatt.todos.data.configuration.RepositoryImplementation
import io.ktor.server.testing.*
import java.time.Duration
import org.kodein.di.*

class TestApplicationFixture(val applicationTestBuilder: ApplicationTestBuilder, val di: DI) {
    suspend fun waitForConsistency() {
        val commandBus by di.instance<CommandBus>()
        val eventsRepository by di.instance<EventsRepository>()
        (commandBus as InMemoryCommandBus).waitForEmptyCommandPropagationQueue(
            Duration.ofSeconds(5)
        )
        (eventsRepository as InMemoryEventsRepository).waitForEmptyEventPropagationQueue(
            Duration.ofSeconds(5)
        )
    }
}

suspend fun testApplicationFixture(block: suspend TestApplicationFixture.() -> Unit) {
    testApplicationFixture(
        applicationConfiguration(
                configuration,
                listOf(
                    applicationDependenciesModule(configuration),
                    testApplicationFixtureDependenciesModule()
                ),
            )
            .allowDIOverrides(true)
            .build(),
        block
    )
}

suspend fun testApplicationFixture(
    applicationConfiguration: ApplicationConfiguration,
    block: suspend TestApplicationFixture.() -> Unit
) {
    testApplication {
        application { applicationConfiguration.applyTo(this) }

        block(TestApplicationFixture(this, applicationConfiguration.di))
    }
}

private val configuration =
    Configuration(
        DataConfiguration(RepositoryImplementation.InMemory),
        EventStoreConfiguration(null, true),
        MongoDBConfiguration(null, "todos"),
        RabbitMQConfiguration(true, null, "todos")
    )

private fun testApplicationFixtureDependenciesModule(): DI.Module =
    DI.Module(name = ::testApplicationFixtureDependenciesModule.name) {
        bindSingleton<Clock>(overrides = true) {
            FakeClock(TimeUtilities.instantOf(2023, 1, 1, 12, 0, 0))
        }
    }
