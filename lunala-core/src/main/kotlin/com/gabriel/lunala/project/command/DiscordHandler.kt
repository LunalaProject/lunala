package com.gabriel.lunala.project.command

import com.gabriel.lunala.project.LunalaDiscord
import com.gitlab.kordlib.core.entity.channel.TextChannel
import com.gitlab.kordlib.core.event.message.MessageCreateEvent
import com.gitlab.kordlib.core.on

class DiscordCommandHandler(private val lunala: LunalaDiscord) {

    suspend fun setup() = lunala.client.on<MessageCreateEvent> {
        if (message.content.startsWith(PREFIX).not() || message.content.length == PREFIX.length || member == null || guildId == null) return@on

        val content = message.content.substring(PREFIX.length).trim().split(" ").ifEmpty { return@on }
        val args = content.drop(1).toMutableList()

        val command = lunala.prototypeService
            .getCommand(content.first(), true)
            ?.let { base -> reformulate(base, args) } ?: return@on

        val guild = message.getGuild()
        val channel = message.getChannel() as? TextChannel ?: return@on

        val profile = lunala.getProfileById(message.author!!.id.longValue, createIfNull = true)!!
        val server = lunala.getServerById(guildId!!.longValue, createIfNull = true)!!

        val context = DiscordCommandContext(content[0], profile, server, args, command, kord, member!!, guild, message, channel)

        dispatch(context)
    }

    companion object {
        const val PREFIX = "s@"
    }
}

private suspend fun dispatch(context: DiscordCommandContext) {
    context.command.trigger?.invoke(context)
}

private tailrec fun reformulate(command: CommandDSL<*>, arguments: MutableList<String>): CommandDSL<*> {
    val match = command.subcommands.firstOrNull { it.name.plus(it.aliases).contains(arguments.firstOrNull() ?: return command) } ?: return command

    arguments.remove(arguments.first())
    return reformulate(match, arguments)
}