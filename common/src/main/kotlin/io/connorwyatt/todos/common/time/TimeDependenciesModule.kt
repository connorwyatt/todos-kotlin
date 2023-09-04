package io.connorwyatt.todos.common.time

import io.connorwyatt.todos.common.time.clock.Clock
import io.connorwyatt.todos.common.time.clock.RealClock
import org.kodein.di.*

val timeDependenciesModule by DI.Module { bind<Clock> { provider { new(::RealClock) } } }
