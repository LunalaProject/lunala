package com.gabriel.lunala.project.command.impl.administration

import com.gabriel.lunala.project.command.Command
import com.gabriel.lunala.project.command.handler.DiscordCommandContext
import com.gabriel.lunala.project.command.snapshot.SnapshotCommand
import com.gabriel.lunala.project.command.utils.command
import com.gabriel.lunala.project.command.utils.explain
import com.gabriel.lunala.project.command.utils.fail
import com.gabriel.lunala.project.utils.api.color
import com.gabriel.lunala.project.utils.api.emote
import com.gabriel.lunala.project.utils.embed.embed
import com.gabriel.lunala.project.utils.flaging.Priority
import com.gabriel.lunala.project.utils.message.DiscordReply
import com.gabriel.lunala.project.utils.state.State
import org.jetbrains.exposed.sql.transactions.transaction

class QueryCommand: SnapshotCommand {

    override fun create(): Command = command("query") {
        description {
            "Execute a update at the"
        }

        example {
            "UPDATE lunalaProfiles SET x=20 WHERE id=360162870069166080"
        }

        shard<DiscordCommandContext>(priority = Priority.SEVERE) {
            if (args.isEmpty()) fail {
                explain()
            }

            val time = System.currentTimeMillis()
            val result = kotlin.runCatching {
                transaction {
                    exec(args.joinToString(" "))
                }
            }

            val state = State(result)

            reply(DiscordReply(embed = embed {
                header {
                    title = "Status"
                    description = buildString {
                        append("${state.emote} The command was executed and the operation **${state.state}**!")
                        append("```${result.exceptionOrNull()?.javaClass?.name?.plus(": ${result.exceptionOrNull()?.message}") ?: "Done in ${System.currentTimeMillis() - time}ms"}```")
                    }
                }
                images {
                    color = state.color
                }
            }, mentionable = profile))
        }
    }

}