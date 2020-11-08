// Todo: FIX

package com.gabriel.lunala.project.util.command

import com.gabriel.lunala.project.util.LunalaProfile
import com.gabriel.lunala.project.util.LunalaReply
import com.gitlab.kordlib.core.behavior.channel.MessageChannelBehavior
import com.gitlab.kordlib.core.behavior.channel.createMessage
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

    fun compile(target: LunalaProfile?) = MessageCreateBuilder().apply {
        content = replies
            .map { reply -> reply.compile(target) }
            .joinToString("\n")
        embed = replies
            .map { reply -> (reply as? LunalaEmbeddableReply)?.embed }
            .firstOrNull()
    }

}

suspend fun MessageChannelBehavior.reply(target: LunalaProfile? = null, reply: ReplyDSL) {
    createMessage {
        reply.compile(target).let {
            content = it.content
            embed = it.embed
        }
    }
}

suspend fun MessageChannelBehavior.reply(target: LunalaProfile? = null, builder: ReplyDSL.() -> Unit) =
    reply(target, ReplyDSL().apply(builder))