package com.gabriel.lunala.project.utils.events

import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.hooks.EventListener
import net.dv8tion.jda.api.hooks.ListenerAdapter

fun EventHolder.toListener(): EventListener = object: ListenerAdapter() {

    override fun onGenericEvent(event: GenericEvent) = this@toListener.onEvent(event)

}