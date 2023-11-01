package io.connorwyatt.todos.restapi.app

import io.connorwyatt.common.configuration.loadConfigurationFromJsonFiles
import io.connorwyatt.common.eventstore.events.EventsRepository
import io.connorwyatt.common.eventstore.events.InMemoryEventsRepository
import io.connorwyatt.common.rabbitmq.bus.CommandBus
import io.connorwyatt.common.rabbitmq.bus.InMemoryCommandBus
import io.connorwyatt.common.time.TimeUtilities
import io.connorwyatt.common.time.clock.Clock
import io.connorwyatt.common.time.clock.testing.FakeClock
import io.ktor.server.testing.*
import java.time.Duration
import kotlinx.coroutines.runBlocking
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

fun testApplicationFixture(block: suspend TestApplicationFixture.() -> Unit) {
    testApplicationFixture(
        DI {
            import(applicationDependenciesModule(configuration))
            bindSingleton<Clock>(overrides = true) {
                FakeClock(TimeUtilities.instantOf(2023, 1, 1, 12, 0, 0))
            }
        }
    ) {
        block(this)
    }
}

fun testApplicationFixture(di: DI, block: suspend TestApplicationFixture.() -> Unit) {
    testApplication {
        application { runBlocking { module(configuration, di) } }

        block(TestApplicationFixture(this, di))
    }
}

private val configuration = loadConfigurationFromJsonFiles<Configuration>("configuration", "test")
