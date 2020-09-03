package com.gabriel.lunala.project.command.impl.utils

import com.gabriel.lunala.project.command.Command
import com.gabriel.lunala.project.command.api.command
import com.gabriel.lunala.project.command.handler.DiscordCommandContext
import com.gabriel.lunala.project.command.snapshot.SnapshotCommand

class StatusCommand: SnapshotCommand {

    override fun create(): Command = command("status", "botsatus") {
        description {
            "See bot's status"
        }

        examples {
            emptyList()
        }

        shard<DiscordCommandContext> {

        }
    }

}