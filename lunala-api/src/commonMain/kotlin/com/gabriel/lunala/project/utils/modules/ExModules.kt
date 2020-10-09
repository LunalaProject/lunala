package com.gabriel.lunala.project.utils.modules

import com.gabriel.lunala.project.Lunala
import com.gabriel.lunala.project.command.Command
import com.gabriel.lunala.project.command.handler.CommandHolder
import com.gabriel.lunala.project.module.LunalaModule
import com.gabriel.lunala.project.utils.events.EventHolder
import com.gabriel.lunala.project.utils.events.LunalaEventManager
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.inject
import org.koin.core.module.Module

private val holder: CommandHolder by Lunala.inject()
private val manager: LunalaEventManager by Lunala.inject()

fun LunalaModule.registerCommands(vararg all: Command): Unit =
        all.forEach { commands.add(it); holder.register(it) }

fun LunalaModule.unregisterCommands(vararg all: Command): Unit =
        all.forEach { commands.add(it); holder.commands.remove(it.labels) }

fun LunalaModule.registerListeners(vararg all: EventHolder): Unit =
        all.forEach { listeners.add(it); manager.register(it) }

fun LunalaModule.unregisterListeners(vararg all: EventHolder): Unit =
        all.forEach { listeners.add(it); manager.unregister(it) }
  
fun LunalaModule.registerDependencies(callback: Module.() -> Unit): Unit =
        modules.add(Module(createAtStart = false, override = false).apply(callback).also { loadKoinModules(it) }).let { Unit }

fun LunalaModule.unregisterDependencies(callback: Module.() -> Unit): Unit =
        modules.remove(Module(createAtStart = false, override = false).apply(callback).also { unloadKoinModules(it) }).let { Unit }
