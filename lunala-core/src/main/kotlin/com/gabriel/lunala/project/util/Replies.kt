package com.gabriel.lunala.project.util

import arrow.Kind
import arrow.fx.ForIO
import arrow.fx.IO
import arrow.fx.extensions.io.async.async
import arrow.fx.typeclasses.Async
import com.gitlab.kordlib.core.behavior.channel.MessageChannelBehavior
import com.gitlab.kordlib.core.behavior.channel.createMessage
import com.gitlab.kordlib.core.entity.Message
import com.gitlab.kordlib.rest.builder.message.EmbedBuilder
import com.gitlab.kordlib.rest.builder.message.MessageCreateBuilder

class LunalaEmbeddableReply(
    prefix: String,
    message: String,
    mentions: Boolean,
    var embed: EmbedBuilder? = null,
): LunalaReply(prefix, message, mentions)

operator fun LunalaReply.Companion.invoke(message: String? = null, embed: EmbedBuilder.() -> Unit): LunalaReply =
    LunalaEmbeddableReply(":large_blue_diamond:", message.orEmpty(), true, EmbedBuilder().apply(embed))

class ReplyDSL {

    val replies = mutableListOf<LunalaReply>()

    fun append(reply: LunalaEmbeddableReply.() -> Unit) {
        replies.add(LunalaEmbeddableReply(":large_blue_diamond:", "", replies.isEmpty()).apply(reply))
    }

    fun compile(target: MentionAware?) = MessageCreateBuilder().apply {
        content = replies.joinToString("\n") { it.compile(target) }
        embed = replies.map { (it as? LunalaEmbeddableReply)?.embed }.firstOrNull()
    }

}

fun <F> MessageChannelBehavior.reply(async: Async<F>, target: MentionAware? = null, reply: ReplyDSL): Kind<F, Message> = async.effect {
    createMessage {
        reply.compile(target).let {
            content = it.content
            embed = it.embed
        }
    }
}

fun MessageChannelBehavior.reply(target: MentionAware? = null, builder: ReplyDSL.() -> Unit): Kind<ForIO, Message> =
    reply(IO.async(), target, ReplyDSL().apply(builder))