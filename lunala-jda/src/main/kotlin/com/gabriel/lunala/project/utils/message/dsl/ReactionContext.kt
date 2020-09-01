package com.gabriel.lunala.project.utils.message.dsl

import com.gabriel.lunala.project.utils.client.on
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent

class ReactionContext(
        val message: Message,
        val emoji: String
) {

    fun recycle() {
        recycling.add(message)
    }

}

private val recycling = mutableSetOf<Message>()

fun Message.onReaction(
        user: User? = null,
        emote: String? = null,
        callback: suspend ReactionContext.() -> Unit
) = jda.on<GuildMessageReactionAddEvent> {
    println(this)

    if (recycling.contains(this@onReaction)) return@on

    if (user != null && userId != user.id) return@on
    if (reactionEmote.isEmote || (emote != null && emote != reactionEmote.asCodepoints)) return@on

    callback(ReactionContext(this@onReaction, reactionEmote.asCodepoints))
}.run { Unit }