package io.connorwyatt.todos.common.messaging.commands

import kotlin.reflect.KClass

class CommandMapDefinition(internal val type: String, internal val clazz: KClass<out Command>)
