package com.gabriel.lunala.project.command.utils

import com.gabriel.lunala.project.command.Command
import com.gabriel.lunala.project.command.CommandBuilder
import com.gabriel.lunala.project.command.CommandContext
import com.gabriel.lunala.project.command.exception.FailException
import com.gabriel.lunala.project.command.handler.DiscordCommandContext
import com.gabriel.lunala.project.utils.CommandDslMarker
import com.gabriel.lunala.project.utils.Mentionable
import com.gabriel.lunala.project.utils.message.LunaReply

@CommandDslMarker
inline fun command(vararg labels: String, block: CommandBuilder.() -> Unit): Command =
        CommandBuilder(labels.toList()).apply(block).create()

fun CommandContext.fail(callback: suspend () -> Unit): Nothing =
        throw FailException(this, callback)

fun DiscordCommandContext.fail(prefix: String, message: String, target: Mentionable? = null): Nothing = throw FailException(this) {
    reply(LunaReply(prefix, message, target))
}