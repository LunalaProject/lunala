package com.gabriel.lunala.project.command.impl

import com.gabriel.lunala.project.command.Command
import com.gabriel.lunala.project.command.api.command
import com.gabriel.lunala.project.command.handler.DiscordCommandContext
import com.gabriel.lunala.project.command.snapshot.SnapshotCommand
import com.gabriel.lunala.project.utils.message.LunalaReply
import com.gabriel.lunala.project.utils.text.Case
import com.gabriel.lunala.project.utils.text.convert

class TestCommand: SnapshotCommand {

    override fun create(): Command = command("testing", "test") {
        shard<DiscordCommandContext> {
            reply(LunalaReply(
                    ":tada:",
                    "My ultra cool message!",
                    mentionable = profile
            ))
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
