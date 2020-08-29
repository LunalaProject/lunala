package com.gabriel.lunala.project.command.impl.status

import com.gabriel.lunala.project.command.Command
import com.gabriel.lunala.project.command.api.command
import com.gabriel.lunala.project.command.handler.DiscordCommandContext
import com.gabriel.lunala.project.command.snapshot.SnapshotCommand
import com.gabriel.lunala.project.utils.embed
import com.gitlab.kordlib.kordx.emoji.Emojis

class StatusCommand: SnapshotCommand {

    override fun create(): Command = command("status", "botsatus") {
        description {
            "See bot's status"
        }

        examples {
            emptyList()
        }

        shard<DiscordCommandContext> {

            val status = embed {

                title = "${Emojis.satelliteOrbital.unicode} | Status"

                author {
                    name = ""

                }

                field {

                }

            }



        }
    }

}