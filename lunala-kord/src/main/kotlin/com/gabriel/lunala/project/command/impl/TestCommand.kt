package com.gabriel.lunala.project.command.impl

import com.gabriel.lunala.project.achievements.AchievementHandler
import com.gabriel.lunala.project.achievements.Achievements
import com.gabriel.lunala.project.command.Command
import com.gabriel.lunala.project.command.api.command
import com.gabriel.lunala.project.command.handler.DiscordCommandContext
import com.gabriel.lunala.project.command.snapshot.SnapshotCommand
import com.gabriel.lunala.project.utils.message.LunalaReply
import org.koin.core.get

class TestCommand: SnapshotCommand {

    override fun create(): Command = command("testing", "test") {
        shard<DiscordCommandContext> {

            get<AchievementHandler>().send(profile, channel, Achievements.ALL_READY_FOR_THE_LAUNCHING)

        }
        shard<DiscordCommandContext>("subcommand") {
            reply(LunalaReply(
                    ":tada:",
                    "My ultra cool message sent in the subcommand!",
                    mentionable = profile
            ))
        }
    }

}
