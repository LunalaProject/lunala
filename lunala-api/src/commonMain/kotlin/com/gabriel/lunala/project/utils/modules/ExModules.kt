package com.gabriel.lunala.project.utils.modules

import com.gabriel.lunala.project.Lunala
import com.gabriel.lunala.project.command.Command
import com.gabriel.lunala.project.command.handler.CommandHolder
import com.gabriel.lunala.project.module.LunalaModule
import com.gabriel.lunala.project.utils.events.EventHolder
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.inject
import org.koin.core.module.Module

private val holder: CommandHolder by Lunala.inject()

fun LunalaModule.registerCommands(vararg all: Command): Unit =
        commands.addAll(all).let { Unit }

fun LunalaModule.unregisterCommands(vararg all: Command): Unit =
        commands.removeAll(all).let { Unit }

fun LunalaModule.registerListeners(vararg all: EventHolder): Unit =
        listeners.addAll(all).let { Unit }

fun LunalaModule.unregisterListeners(vararg all: EventHolder): Unit =
        listeners.removeAll(all).let { Unit }

fun LunalaModule.registerDependencies(callback: Module.() -> Unit): Unit =
        modules.add(Module(createAtStart = false, override = false).apply(callback).also { loadKoinModules(it) }).let { Unit }

fun LunalaModule.unregisterDependencies(callback: Module.() -> Unit): Unit =
        modules.remove(Module(createAtStart = false, override = false).apply(callback).also { unloadKoinModules(it) }).let { Unit }
