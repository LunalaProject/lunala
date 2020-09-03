package com.gabriel.lunala.project.utils.boot

import com.gabriel.lunala.project.Lunala
import com.gabriel.lunala.project.LunalaJDA
import com.gabriel.lunala.project.command.handler.CommandHolder
import com.gabriel.lunala.project.command.handler.DiscordCommandHandler
import com.gabriel.lunala.project.command.impl.utils.StatusCommand
import com.gabriel.lunala.project.utils.observer.ReactionObserver
import net.dv8tion.jda.api.sharding.ShardManager
import org.koin.core.get

internal fun LunalaJDA.registerCommands() = Lunala.get<CommandHolder>().let {
    it.register(StatusCommand())
}

internal fun LunalaJDA.registerListeners() = Lunala.get<ShardManager>().let {
    it.addEventListener(ReactionObserver)
    it.addEventListener(DiscordCommandHandler())
}