
package com.gabriel.lunala.project.utils.boot

import com.gabriel.lunala.project.Lunala
import com.gabriel.lunala.project.LunalaJDA
import com.gabriel.lunala.project.command.handler.CommandHandler
import com.gabriel.lunala.project.command.handler.CommandHolder
import com.gabriel.lunala.project.command.handler.DiscordCommandContext
import com.gabriel.lunala.project.command.handler.DiscordCommandHandler
import com.gabriel.lunala.project.command.impl.administration.RestartCommand
import com.gabriel.lunala.project.command.impl.utils.StatusCommand
import com.gabriel.lunala.project.utils.observer.ReactionObserver
import net.dv8tion.jda.api.sharding.ShardManager
import org.koin.core.context.loadKoinModules
import org.koin.core.get
import org.koin.core.inject
import org.koin.dsl.koinApplication
import org.koin.dsl.module

private val handler: CommandHandler<DiscordCommandContext> by Lunala.inject()
private val holder: CommandHolder by Lunala.inject()
private val manager: ShardManager by Lunala.inject()

internal fun LunalaJDA.registerCommands() = holder.let {
    it.register(StatusCommand())
    it.register(RestartCommand())
}

private val observer = Lunala.get<ReactionObserver>()

internal fun LunalaJDA.registerListeners() = manager.let {
    it.addEventListener(observer)
    it.addEventListener(handler)

    loadKoinModules(module {
        single { observer }
    })
}
