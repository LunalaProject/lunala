package com.gabriel.lunala.project.utils.message

import com.gabriel.lunala.project.utils.Mentionable
import com.gitlab.kordlib.core.behavior.channel.createMessage
import com.gitlab.kordlib.core.entity.Message
import com.gitlab.kordlib.core.entity.channel.MessageChannel
import com.gitlab.kordlib.rest.builder.message.EmbedBuilder

class DiscordReply(
        prefix: String = ":large_blue_diamond:",
        content: String = "",
        mentionable: Mentionable? = null,
        val embed: EmbedBuilder
): LunalaReply(prefix, content, mentionable) {

    suspend fun asMessage(channel: MessageChannel): Message = channel.createMessage {
        this@createMessage.content = format()
        this@createMessage.embed = this@DiscordReply.embed
    }

}
