package com.gabriel.lunala.project.commands

import com.gabriel.lunala.project.command.Command
import com.gabriel.lunala.project.command.handler.DiscordCommandContext
import com.gabriel.lunala.project.command.snapshot.SnapshotCommand
import com.gabriel.lunala.project.command.utils.command
import com.gabriel.lunala.project.route.DefaultBattleRoute
import com.gabriel.lunala.project.utils.flaging.Priority

class TestBattleCommand: SnapshotCommand {

    override fun create(): Command = command("samplebattle") {
        shard<DiscordCommandContext>(priority = Priority.SEVERE) {
            DefaultBattleRoute().init(profile, channel)
        }
    }

}