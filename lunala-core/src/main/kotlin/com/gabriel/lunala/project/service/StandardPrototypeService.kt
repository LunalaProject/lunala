package com.gabriel.lunala.project.service

import com.gabriel.lunala.project.LunalaDiscord
import com.gabriel.lunala.project.command.CommandDSL
import com.gabriel.lunala.project.command.DiscordCommandHandler
import com.gabriel.lunala.project.command.impl.misc.PingCommand
import com.gabriel.lunala.project.listener.MentionListener
import kotlinx.coroutines.Job

class StandardPrototypeService(val lunala: LunalaDiscord): PrototypeService {

    override val commands: MutableList<CommandDSL<*>> = mutableListOf()
    override val listeners: MutableList<Job> = mutableListOf()

    override suspend fun start() {
        registerCommands()
        registerListeners()

        DiscordCommandHandler(lunala).setup()
    }

    private fun registerCommands() = run {
        commands.add(PingCommand().create())
    }

    private fun registerListeners() = run {
        listeners.add(MentionListener(lunala).create())
    }

    override fun getCommand(name: String, aliases: Boolean): CommandDSL<*>? = commands.firstOrNull {
        it.name.plus(if (aliases) it.aliases else null).contains(name)
    }
}