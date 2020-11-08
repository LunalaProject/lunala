package com.gabriel.lunala.project.command

import com.gabriel.lunala.project.util.LunalaGuild
import com.gabriel.lunala.project.util.LunalaProfile
import com.gabriel.lunala.project.util.LunalaReply
import com.gabriel.lunala.project.util.command.ReplyDSL
import com.gabriel.lunala.project.util.command.reply
import com.gitlab.kordlib.core.Kord
import com.gitlab.kordlib.core.entity.Guild
import com.gitlab.kordlib.core.entity.Member
import com.gitlab.kordlib.core.entity.Message
import com.gitlab.kordlib.core.entity.channel.TextChannel

class DiscordCommandContext(
    override val label: String,
    override val profile: LunalaProfile,
    override val server: LunalaGuild,
    override val args: List<String>,
    override val command: CommandDSL<*>,
    val kord: Kord,
    val user: Member,
    val guild: Guild,
    val message: Message,
    val channel: TextChannel,
) : CommandContext {

    suspend fun reply(init: LunalaReply? = null, dsl: ReplyDSL.() -> Unit) = channel.reply(profile) {
        if (init != null) {
            replies.add(init)
        }; dsl(this)
    }

}