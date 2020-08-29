package com.gabriel.lunala.project.utils

import com.gabriel.lunala.project.utils.message.DiscordReply
import com.gabriel.lunala.project.utils.message.LunaReply
import com.gitlab.kordlib.common.entity.Snowflake
import com.gitlab.kordlib.core.behavior.channel.createMessage
import com.gitlab.kordlib.core.entity.channel.MessageChannel
import com.gitlab.kordlib.rest.builder.message.EmbedBuilder

fun embed(
        block: EmbedBuilder.() -> Unit
): EmbedBuilder =
        EmbedBuilder().apply(block)

suspend fun MessageChannel.createMessage(
        reply: LunaReply
) = createMessage {
    content = reply.format()

    if (reply is DiscordReply) embed = reply.embed
}

fun Long.toSnowflake(): Snowflake =
        Snowflake(this)

fun String.toSnowflake(): Snowflake =
        Snowflake(this)