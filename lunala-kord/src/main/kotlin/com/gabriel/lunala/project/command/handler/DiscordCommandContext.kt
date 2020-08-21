package com.gabriel.lunala.project.command.handler

import com.gabriel.lunala.project.command.Command
import com.gabriel.lunala.project.command.CommandContext
import com.gabriel.lunala.project.command.snapshot.ShardCommand
import com.gabriel.lunala.project.entity.Profile
import com.gabriel.lunala.project.entity.Server
import com.gabriel.lunala.project.utils.createMessage
import com.gabriel.lunala.project.utils.message.LunaReply
import com.gitlab.kordlib.core.entity.Guild
import com.gitlab.kordlib.core.entity.Member
import com.gitlab.kordlib.core.entity.Message
import com.gitlab.kordlib.core.entity.channel.TextChannel
import org.koin.core.KoinComponent

class DiscordCommandContext(
        override val command: Command,
        override val label: String,
        override val profile: Profile,
        override val server: Server,
        override val args: List<String>,
        override val shard: ShardCommand,

        val message: Message,
        val member: Member,
        val guild: Guild,
        val channel: TextChannel
) : CommandContext, KoinComponent {

    suspend fun reply(reply: LunaReply): Message = channel.createMessage(reply)

}