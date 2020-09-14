package com.gabriel.lunala.project.utils.observer

import com.gabriel.lunala.project.Lunala
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.inject

@Suppress("EXPERIMENTAL_API_USAGE")
class ReactionObserver: ListenerAdapter(), KoinComponent {

    private val channel = BroadcastChannel<MessageReactionAddEvent>(Channel.CONFLATED)
    private val flow = channel.asFlow()

    private val scope: CoroutineScope by inject()

    fun on(
            message: Message,
            user: User? = null,
            emoji: String? = null,
            callback: suspend MessageReactionAddEvent.() -> Unit
    ): Job = flow.buffer(Channel.UNLIMITED).filter {
        (message.idLong == it.messageIdLong) && (user == null || it.userId != user.id) && (emoji == null || it.reactionEmote.isEmoji && emoji != it.reactionEmote.asCodepoints)
    }.onEach(callback).launchIn(scope)

    override fun onMessageReactionAdd(event: MessageReactionAddEvent): Unit = scope.launch {
        channel.send(event)
    }.run {
        Unit
    }

}

fun Message.onReaction(
        user: User? = null,
        emoji: String? = null,
        callback: suspend MessageReactionAddEvent.() -> Unit
): Job = Lunala.get<ReactionObserver>().on(this, user, emoji, callback)