package com.gabriel.lunala.project.module

import com.gabriel.lunala.project.Lunala
import com.gabriel.lunala.project.command.Command
import kotlinx.coroutines.Dispatchers
import net.dv8tion.jda.api.hooks.EventListener
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File
import kotlin.coroutines.CoroutineContext

open class StandardModule(
        name: String,
        lunala: Lunala,
        enabled: Boolean = false,
        coroutineContext: CoroutineContext = Dispatchers.Default
): LunalaModule(name, lunala, enabled, coroutineContext) {

    internal val commands = mutableSetOf<Command>()
    internal val listeners = mutableListOf<EventListener>()

    internal var file: File? = null

    fun registerCommand(command: Command): Unit =
            commands.add(command).let { Unit }

    fun registerListener(listener: EventListener): Unit =
            listeners.add(listener).let { Unit }

    fun registerComponent(callback: Module.() -> Unit): Unit =
            loadKoinModules(module(moduleDeclaration = callback))

    fun unregisterCommand(command: Command): Unit =
            commands.remove(command).let { Unit }

    fun unregisterListener(listener: EventListener): Unit =
            listeners.remove(listener).let { Unit }

}