package com.gabriel.lunala.project.command.impl.status

import com.gabriel.lunala.project.command.Command
import com.gabriel.lunala.project.command.api.command
import com.gabriel.lunala.project.command.handler.DiscordCommandContext
import com.gabriel.lunala.project.command.snapshot.SnapshotCommand
import com.gabriel.lunala.project.utils.message.LunaReply
import com.gabriel.lunala.project.utils.message.dsl.onReaction

class StatusCommand: SnapshotCommand {

    override fun create(): Command = command("status", "botsatus") {
        description {
            "See bot's status"
        }

        examples {
            emptyList()
        }

        shard<DiscordCommandContext> {

            reply(LunaReply(
                    ":tada:",
                    "Test"
            )).onReaction(member.user) {
                message.editMessage("Você clickou!").queue()
                recycle()
            }
        }
    }

}