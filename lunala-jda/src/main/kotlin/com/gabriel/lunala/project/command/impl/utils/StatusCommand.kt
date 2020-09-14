package com.gabriel.lunala.project.command.impl.utils

import com.gabriel.lunala.project.command.Command
import com.gabriel.lunala.project.command.handler.DiscordCommandContext
import com.gabriel.lunala.project.command.snapshot.SnapshotCommand
import com.gabriel.lunala.project.command.utils.command
import com.gabriel.lunala.project.utils.embed.embed
import com.gabriel.lunala.project.utils.message.DiscordReply
import kotlinx.coroutines.future.await
import java.awt.Color
import java.lang.management.ManagementFactory

class StatusCommand: SnapshotCommand {

    override fun create(): Command = command("status", "botsatus") {
        description {
            "See bot's status"
        }

        examples {
            emptyList()
        }

        shard<DiscordCommandContext> {
            val runtime = Runtime.getRuntime()
            val restPing = client.restPing.submit().await()

            val status = embed {
                header {
                    title = "❤️ Bot Status"
                    description = "Dont disturb other people asking if I'm down, check <#703981215820021772> channel from my official support discord server and see if the issue was identified."
                }
                fieldset {
                    field {
                        name = "\uD83D\uDEF0 Shard"
                        value = client.shardInfo.shardString
                        inline = true
                    }
                    field {
                        name = "\t\uD83C\uDFD3 Rest API"
                        value = restPing.toString() + "ms"
                        inline = true
                    }
                    field {
                        name = "\t\uD83D\uDCE1 Gateway"
                        value = client.gatewayPing.toString() + "ms"
                        inline = true
                    }
                    field {
                        name = "\uD83D\uDDD1 Used Memory"
                        value = ((runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024)).toString() + "MB"
                        inline = true
                    }
                    field {
                        name = "\uD83D\uDC77 Threads"
                        value = ManagementFactory.getThreadMXBean().threadCount
                        inline = true
                    }
                    field {
                        name = "⚙️ Processors"
                        value = runtime.availableProcessors()
                        inline = true
                    }
                }
                images {
                    color = Color(238, 111, 0)
                    thumbnail = client.selfUser.effectiveAvatarUrl
                }
                footer {
                    text = "\uD83D\uDC9E Developed with love by Lunala Team!"
                    icon = member.user.effectiveAvatarUrl
                }
            }

            this.reply(DiscordReply(
                    mentionable = profile,
                    embed = status
            ))
        }
    }

}