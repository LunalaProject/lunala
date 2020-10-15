package com.gabriel.lunala.project.command

import arrow.Kind
import arrow.core.Tuple2
import arrow.fx.ForIO
import arrow.fx.IO
import arrow.fx.extensions.fx
import arrow.fx.extensions.io.applicativeError.attempt
import com.gabriel.lunala.project.platform.LunalaCluster
import com.gabriel.lunala.project.util.ProfileService
import com.gabriel.lunala.project.util.ServerService
import com.gitlab.kordlib.core.entity.channel.TextChannel
import com.gitlab.kordlib.core.event.message.MessageCreateEvent
import com.gitlab.kordlib.core.on
import java.nio.file.WatchEvent

class DiscordCommandHandler(
    private val cluster: LunalaCluster,
    private val services: Tuple2<ProfileService, ServerService>
) {

    suspend fun setup() = cluster.client.on<MessageCreateEvent> {
        if (message.content.startsWith(PREFIX).not() || message.content.length == PREFIX.length)
            return@on
        if (member == null || guildId == null)
            return@on

        val content = message.content.substring(PREFIX.length).trim().split(" ")

        val base = CommandRepository.retrieveWithAliases(content.firstOrNull() ?: return@on) ?: return@on
        val command = getCommandDSL(content) ?: return@on

        val args = breakArgs(base, command, content.drop(1).toMutableList())

        val guild = message.getGuild()
        val channel = message.getChannel() as? TextChannel ?: return@on

        val contextIo = IO.fx {
            val profile = services.a.findOrCreateById(message.author!!.id.longValue).bind()
            val server = services.b.findOrCreateById(guildId!!.longValue).bind()

            DiscordCommandContext(content[0], profile, server, args, command, kord, member!!, guild, message, channel, cluster)
        }

        dispatch(contextIo.suspended()).attempt().suspended()
    }

    private suspend fun dispatch(context: DiscordCommandContext): Kind<ForIO, Unit> = IO {
        val trigger = context.command.trigger!!
        trigger.invoke(context)
    }

    private fun getCommandDSL(content: List<String>): CommandDSL<*>? {
        val command = CommandRepository.retrieve(content.firstOrNull() ?: return null) ?: return null
        var subcommand: CommandDSL<*> = command

        for (argument in content.drop(1)) {
            subcommand = command.subcommands.firstOrNull { it.name == argument || it.aliases.contains(argument) } ?: break
        }

        return subcommand
    }

    private fun breakArgs(base: CommandDSL<*>, compiled: CommandDSL<*>, args: List<String>): MutableList<String> {
        val final = args.toMutableList()
        for ((index, command) in base.subcommands.withIndex()) {
            if (compiled == command) {
                final.drop(index)
            }
        }
        return final
    }

    companion object {

        const val PREFIX = ">"

    }

}