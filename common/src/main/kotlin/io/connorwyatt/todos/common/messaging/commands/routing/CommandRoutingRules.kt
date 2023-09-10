package io.connorwyatt.todos.common.messaging.commands.routing

import io.connorwyatt.todos.common.messaging.commands.Command
import kotlin.reflect.KClass

internal data class CommandRoutingRules(
    private val defaultQueueName: String,
    private val rules: Map<KClass<out Command>, String>
) {
    internal fun queueFor(clazz: KClass<out Command>): String = rules[clazz] ?: defaultQueueName
}
