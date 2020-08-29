package com.gabriel.lunala.project.utils.message.dsl

import com.gitlab.kordlib.core.entity.Message
import com.gitlab.kordlib.core.entity.User
import com.gitlab.kordlib.core.event.message.ReactionAddEvent
import com.gitlab.kordlib.core.on
import com.gitlab.kordlib.kordx.emoji.DiscordEmoji
import com.gitlab.kordlib.kordx.emoji.toReaction

class ReactionContext(
        val message: Message,
        val emoji: DiscordEmoji
) {

    fun recycle() {
        recycling.add(message)
    }

}

private val recycling = mutableSetOf<Message>()

fun Message.onReaction(
        user: User? = null,
        emote: DiscordEmoji,
        callback: suspend ReactionContext.() -> Unit
) = kord.on<ReactionAddEvent> {
    if (recycling.contains(this@onReaction)) return@on

    if (userId != user?.id) return@on
    if (emoji != emote.toReaction()) return@on

    callback(ReactionContext(this@onReaction, emote))
}.run { Unit }