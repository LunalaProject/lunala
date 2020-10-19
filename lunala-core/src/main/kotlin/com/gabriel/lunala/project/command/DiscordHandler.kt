package com.gabriel.lunala.project.command

import arrow.Kind
import arrow.core.Tuple2
import arrow.core.Tuple3
import arrow.fx.ForIO
import arrow.fx.IO
import arrow.fx.extensions.fx
import arrow.fx.extensions.io.applicativeError.attempt
import com.gabriel.lunala.project.platform.LunalaCluster
import com.gabriel.lunala.project.service.CommandService
import com.gabriel.lunala.project.util.ProfileService
import com.gabriel.lunala.project.util.ServerService
import com.gitlab.kordlib.core.entity.channel.TextChannel
import com.gitlab.kordlib.core.event.message.MessageCreateEvent
import com.gitlab.kordlib.core.on
import java.nio.file.WatchEvent

class DiscordCommandHandler(
    private val cluster: LunalaCluster,
    private val services: Tuple3<ProfileService, ServerService, CommandService>
) {

    suspend fun setup() = cluster.client.on<MessageCreateEvent> {
        if (message.content.startsWith(PREFIX).not() || message.content.length == PREFIX.length)
            return@on
        if (member == null || guildId == null)
            return@on

        val content = message.content.substring(PREFIX.length).trim().split(" ")

        val base = services.c.repository.retrieveWithAliases(content.firstOrNull() ?: return@on) ?: return@on
        val command = getCommandDSL(base, content) ?: return@on

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

    private fun getCommandDSL(base: CommandDSL<*>, content: List<String>): CommandDSL<*>? {
        var subcommand: CommandDSL<*> = base

        for (argument in content.drop(1)) {
            subcommand = base.subcommands.firstOrNull { it.name == argument || it.aliases.contains(argument) } ?: break
        }

        return subcommand
    }

    private fun breakArgs(base: CommandDSL<*>, compiled: CommandDSL<*>, args: List<String>): List<String> {
        var arguments = args.joinToString(" ")

        for (command in base.subcommands) {
            if (compiled.subcommands.contains(command)) break
            command.aliases.plus(command.name).forEach {
                arguments = arguments.replaceFirst("$it ", "")
            }
        }

        return arguments.trim().split(" ")
    }

    companion object {

        const val PREFIX = "s@"

    }

}