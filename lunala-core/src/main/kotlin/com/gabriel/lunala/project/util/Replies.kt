package com.gabriel.lunala.project.util

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

    fun embed(embed: EmbedBuilder.() -> Unit) {
        append {
            this.embed = EmbedBuilder().apply(embed)
        }
    }

    fun compile(target: MentionAware?) = MessageCreateBuilder().apply {
        content = replies.joinToString("\n") { it.compile(target) }
        embed = replies.map { (it as? LunalaEmbeddableReply)?.embed }.firstOrNull()
    }

}

suspend fun MessageChannelBehavior.reply(target: MentionAware? = null, reply: ReplyDSL): Message = createMessage {
    reply.compile(target).let {
        content = it.content
        embed = it.embed
    }
}

suspend fun MessageChannelBehavior.reply(target: MentionAware? = null, builder: ReplyDSL.() -> Unit): Message =
    reply(target, ReplyDSL().apply(builder))

fun LunalaReply.toDsl() = ReplyDSL().also {
    it.replies.add(this)
}