package com.gabriel.lunala.project.module

import com.gabriel.lunala.project.Lunala
import com.gabriel.lunala.project.command.handler.CommandHolder
import com.gabriel.lunala.project.lifecycle.ILifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.core.KoinComponent
import org.koin.core.inject
import kotlin.coroutines.CoroutineContext
import kotlin.jvm.JvmOverloads

abstract class LunalaModule @JvmOverloads constructor(
        val name: String,
        val lunala: Lunala,
        var enabled: Boolean = false,
        override val coroutineContext: CoroutineContext = Dispatchers.Default
): ILifecycle, CoroutineScope, KoinComponent {

    val holder: CommandHolder by inject()
    val tasks: MutableList<Job> = mutableListOf()

}