package io.connorwyatt.todos.restapi.app

import io.connorwyatt.todos.common.time.TimeUtilities
import io.connorwyatt.todos.common.time.clock.Clock
import io.connorwyatt.todos.common.time.clock.testing.FakeClock
import io.ktor.server.testing.*
import org.kodein.di.*

class TestApplicationFixture(val applicationTestBuilder: ApplicationTestBuilder, val di: DI)

fun testApplicationFixture(block: suspend TestApplicationFixture.() -> Unit) {
    testApplicationFixture(
        DI {
            extend(applicationDIConfiguration)
            bind<Clock>(overrides = true) {
                singleton { FakeClock(TimeUtilities.instantOf(2023, 1, 1, 12, 0, 0)) }
            }
        }
    ) {
        block(this)
    }
}

fun testApplicationFixture(di: DI, block: suspend TestApplicationFixture.() -> Unit) {
    testApplication {
        application { module(di) }

        block(TestApplicationFixture(this, di))
    }
}