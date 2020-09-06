package com.gabriel.lunala.project.utils.client

import com.gabriel.lunala.project.Lunala
import com.gabriel.lunala.project.event.ReactiveEventManager
import com.gabriel.lunala.project.utils.message.DiscordReply
import com.gabriel.lunala.project.utils.message.LunaReply
import kotlinx.coroutines.*
import kotlinx.coroutines.future.asDeferred
import kotlinx.coroutines.future.await
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.events.GenericEvent
import org.koin.core.get
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

fun MessageChannel.sendMessage(vararg reply: LunaReply): Deferred<Message> = this.sendMessage(
    MessageBuilder(reply.joinToString("\n") {
        it.format()
    }).setEmbed((reply as? DiscordReply)?.embed).build()
).submit().asDeferred()


inline fun <reified T : GenericEvent> JDA.on(
        scope: CoroutineScope = Lunala.get(),
        noinline callback: suspend T.() -> Unit
): Unit = (eventManager as? ReactiveEventManager)?.on(T::class.java)?.subscribe {
    scope.launch {
        callback.invoke(it)
    }
}.run {
    Unit
}