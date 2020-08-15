package com.gabriel.lunala.project.command.handler

import com.gabriel.lunala.project.command.Command
import com.gabriel.lunala.project.command.CommandContext
import com.gabriel.lunala.project.command.snapshot.ShardCommand
import com.gabriel.lunala.project.entity.Profile
import com.gabriel.lunala.project.entity.Server
import com.gabriel.lunala.project.utils.message.LunalaReply
import com.gitlab.kordlib.core.entity.Guild
import com.gitlab.kordlib.core.entity.Member
import com.gitlab.kordlib.core.entity.Message
import com.gitlab.kordlib.core.entity.channel.TextChannel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.KoinComponent
import org.koin.core.get
import java.awt.Color
import java.time.Instant

class DiscordCommandContext(
        override val command: Command,
        override val label: String,
        override val profile: Profile,
        override val server: Server,
        override val args: List<String>,
        override val shard: ShardCommand,

        val message: Message
) : CommandContext, KoinComponent {

    lateinit var member: Member
    lateinit var guild: Guild
    lateinit var channel: TextChannel

    init {
        get<CoroutineScope>().launch {
            member = message.getAuthorAsMember()!!
            guild = message.getGuild()
            channel = message.getChannel() as TextChannel
        }
    }

    suspend fun reply(reply: LunalaReply): Message =
        channel.createMessage(reply.format())

}