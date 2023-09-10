package io.connorwyatt.todos.common.messaging.commands.commandhandlers

import io.connorwyatt.todos.common.messaging.commands.Command
import kotlin.reflect.KClass
import org.kodein.di.*

class CommandHandlerDefinition(
    internal val clazz: KClass<out Command>,
    internal val creator: DirectDI.() -> CommandHandler
)
