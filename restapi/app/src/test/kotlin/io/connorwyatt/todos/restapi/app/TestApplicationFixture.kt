package io.connorwyatt.todos.restapi.app

import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.addResourceSource
import io.connorwyatt.todos.common.domain.events.EventsRepository
import io.connorwyatt.todos.common.domain.inmemory.InMemoryEventsRepository
import io.connorwyatt.todos.common.time.TimeUtilities
import io.connorwyatt.todos.common.time.clock.Clock
import io.connorwyatt.todos.common.time.clock.testing.FakeClock
import io.ktor.server.testing.*
import java.time.Duration
import org.kodein.di.*

class TestApplicationFixture(val applicationTestBuilder: ApplicationTestBuilder, val di: DI) {
    suspend fun waitForConsistency() {
        val eventsRepository by di.instance<EventsRepository>()
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
        application { module(configuration, di) }

        block(TestApplicationFixture(this, di))
    }
}

private val configuration =
    ConfigLoaderBuilder.default()
        .apply {
            addResourceSource("/configuration.test.json")
            addResourceSource("/configuration.json")
        }
        .build()
        .loadConfigOrThrow<Configuration>()
