package com.gabriel.lunala.project.module

import com.gabriel.lunala.project.command.Command
import com.gabriel.lunala.project.lifecycle.ILifecycle
import com.gabriel.lunala.project.utils.events.EventHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.core.KoinComponent
import org.koin.core.module.Module
import kotlin.coroutines.CoroutineContext
import kotlin.jvm.JvmOverloads

abstract class LunalaModule @JvmOverloads constructor(
        val name: String,
        override val coroutineContext: CoroutineContext = Dispatchers.Default
): ILifecycle, CoroutineScope, KoinComponent {

    val tasks: MutableList<Job> = mutableListOf()
    val modules: MutableList<Module> = mutableListOf()

    val commands = mutableSetOf<Command>()
    val listeners = mutableSetOf<EventHolder>()

    var enabled: Boolean = false

}