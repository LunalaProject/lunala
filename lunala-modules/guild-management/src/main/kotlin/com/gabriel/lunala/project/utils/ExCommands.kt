package com.gabriel.lunala.project.utils

import com.gabriel.lunala.project.Lunala
import com.gabriel.lunala.project.command.CommandBuilder
import com.gabriel.lunala.project.command.handler.DiscordCommandContext
import com.gabriel.lunala.project.commands.NotifyCommand
import com.gabriel.lunala.project.config.LunalaDiscordConfig
import org.koin.core.inject

private val config: LunalaDiscordConfig by Lunala.inject()

fun CommandBuilder.local(block: suspend DiscordCommandContext.() -> Unit) = shard<DiscordCommandContext> {
    if (guild.idLong == config.discordId) {
        block.invoke(this)
    }
}