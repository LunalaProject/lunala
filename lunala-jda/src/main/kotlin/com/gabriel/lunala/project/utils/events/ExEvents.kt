package com.gabriel.lunala.project.utils.events

import com.gabriel.lunala.project.Lunala
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.hooks.EventListener
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.koin.core.get

fun EventHolder.toListener(scope: CoroutineScope = Lunala.get()): EventListener = object: ListenerAdapter() {

    override fun onGenericEvent(event: GenericEvent) = scope.launch { this@toListener.onEvent(event) }.let { Unit }

}