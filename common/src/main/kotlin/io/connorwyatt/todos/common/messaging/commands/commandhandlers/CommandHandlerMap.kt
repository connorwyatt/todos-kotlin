package io.connorwyatt.todos.common.messaging.commands.commandhandlers

import io.connorwyatt.todos.common.messaging.commands.Command
import kotlin.reflect.KClass

class CommandHandlerMap(private val definitions: Set<CommandHandlerDefinition>) {
    init {
        checkForDuplicates()
    }

    internal fun creatorFor(clazz: KClass<out Command>) =
        definitions.singleOrNull { it.clazz == clazz }?.creator
            ?: throw Exception(
                "Could not find CommandHandler creator for class (${clazz.simpleName})."
            )

    private fun checkForDuplicates() {
        if (definitions.distinct().count() != definitions.count()) {
            throw Exception("Multiple CommandHandlerMap entries registered for some command(s).")
        }
    }
}
