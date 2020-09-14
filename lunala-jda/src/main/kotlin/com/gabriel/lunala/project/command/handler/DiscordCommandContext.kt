package com.gabriel.lunala.project.command.handler

import com.gabriel.lunala.project.command.Command
import com.gabriel.lunala.project.command.CommandContext
import com.gabriel.lunala.project.command.snapshot.ShardCommand
import com.gabriel.lunala.project.entity.Profile
import com.gabriel.lunala.project.entity.Server
import com.gabriel.lunala.project.utils.client.sendMessage
import com.gabriel.lunala.project.utils.message.LunaReply
import kotlinx.coroutines.future.await
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.TextChannel
import org.koin.core.KoinComponent

data class DiscordCommandContext constructor(
        override val command: Command,
        override val label: String,
        override val profile: Profile,
        override val server: Server,
        override val args: List<String>,
        override val shard: ShardCommand,

        val client: JDA,
        val message: Message,
        val member: Member,
        val guild: Guild,
        val channel: TextChannel
) : CommandContext, KoinComponent {

    suspend fun reply(vararg reply: LunaReply): Message = channel.sendMessage(*reply).submit().await()

}