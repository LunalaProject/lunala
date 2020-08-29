package com.gabriel.lunala.project.command.impl.utils

import com.gabriel.lunala.project.command.Command
import com.gabriel.lunala.project.command.api.command
import com.gabriel.lunala.project.command.api.fail
import com.gabriel.lunala.project.command.handler.DiscordCommandContext
import com.gabriel.lunala.project.command.snapshot.SnapshotCommand
import com.gabriel.lunala.project.ore.StandaloneOre
import com.gabriel.lunala.project.utils.embed
import com.gabriel.lunala.project.utils.message.DiscordReply
import com.gabriel.lunala.project.utils.message.LunaReply

class OreCommand: SnapshotCommand {

    override fun create(): Command = command("") {

        description {
            "See info about all ores available"
        }

        examples {
            listOf("Anellite")
        }

        shard<DiscordCommandContext> {
            val ore = StandaloneOre.values.filter {
                it.name == args.getOrNull(0)
            }.firstOrNull() ?: fail {
                reply(LunaReply(":no_entry_sign:", "The provided ore was not found in my system."))
            }

            val embed = embed {

                title = ore.name
                description = "Showing info for ore **${ore.name.toUpperCase()}**:"

                field {
                    name = "\uD83D\uDC8C Name"
                    value = ore.name
                    inline = true
                }

                field {
                    name = "\uD83D\uDE80 Planets"
                    value = ore.planets.joinToString { it.name }
                    inline = true
                }

                field {
                    name = "\uD83D\uDD25 Rarity"
                    value = ore.rarity.name
                    inline = true
                }

                footer {
                    text = "Reply to **${member.username}**"
                    icon = member.avatar.url
                }
            }

            reply(DiscordReply(embed = embed))
        }
    }

}