package com.gabriel.lunala.project.command.impl.administration

import com.gabriel.lunala.project.command.Command
import com.gabriel.lunala.project.command.api.command
import com.gabriel.lunala.project.command.handler.DiscordCommandContext
import com.gabriel.lunala.project.command.snapshot.SnapshotCommand
import com.gabriel.lunala.project.utils.api.color
import com.gabriel.lunala.project.utils.api.emote
import com.gabriel.lunala.project.utils.embed.embed
import com.gabriel.lunala.project.utils.message.DiscordReply
import com.gabriel.lunala.project.utils.state.State
import org.jetbrains.exposed.sql.transactions.transaction

class QueryCommand: SnapshotCommand {

    override fun create(): Command = command("query") {
        shard<DiscordCommandContext> {
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