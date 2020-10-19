package com.gabriel.lunala.project.command

import arrow.fx.IO
import arrow.fx.extensions.fx
import com.gabriel.lunala.project.entity.Profile
import com.gabriel.lunala.project.entity.Server
import com.gabriel.lunala.project.locale.LocaleWrapper
import com.gabriel.lunala.project.platform.LunalaCluster
import com.gabriel.lunala.project.util.*
import com.gitlab.kordlib.core.Kord
import com.gitlab.kordlib.core.entity.Guild
import com.gitlab.kordlib.core.entity.Member
import com.gitlab.kordlib.core.entity.Message
import com.gitlab.kordlib.core.entity.channel.TextChannel

class DiscordCommandContext(
    override val label: String,
    override val profile: Profile,
    override val server: Server,
    override val args: List<String>,
    override val command: CommandDSL<*>,
    val kord: Kord,
    val user: Member,
    val guild: Guild,
    val message: Message,
    val channel: TextChannel,
    val cluster: LunalaCluster
) : CommandContext {

    val locale: LocaleWrapper = server.getLocale()

    suspend fun reply(dsl: ReplyDSL.() -> Unit) = IO.fx { channel.reply(profile, dsl).bind() }.attempt().suspended()

}