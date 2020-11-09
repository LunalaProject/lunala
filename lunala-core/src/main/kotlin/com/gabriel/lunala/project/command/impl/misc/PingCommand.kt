package com.gabriel.lunala.project.command.impl.misc

import com.gabriel.lunala.project.command.CommandCategory
import com.gabriel.lunala.project.command.DiscordCommandContext
import com.gabriel.lunala.project.util.command.LunalaEmbeddableReply
import com.gabriel.lunala.project.util.command.command
import com.gabriel.lunala.project.util.lunala
import com.gabriel.lunala.project.util.platform.embed
import java.awt.Color
import java.time.Instant
import kotlin.time.ExperimentalTime

class PingCommand {

    fun create() = command("ping", "status", category = CommandCategory.MISCELLANEOUS) {
        executor {
            reply {
                append {
                    createPingEmbed(this@executor)
                }
            }
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun LunalaEmbeddableReply.createPingEmbed(context: DiscordCommandContext) {
        embed = embed {
            title = "\uD83C\uDFD3 Lunala Status"
            color = Color(0, 128, 255)
            description = "This is my current status, if I'm unstable, don't worry. I'll try to be back as soon as possible!"

            thumbnail {
                url = context.user.avatar.url
            }

            field {
                name = "\uD83E\uDDEA Ping"
                value = "${context.kord.gateway.averagePing.toLongMilliseconds()}ms"
                inline = true
            }
            field {
                name = "\uD83D\uDC8E Shards"
                value = "${context.kord.resources.shardCount}"
                inline = true
            }
            field {
                name = "\uD83D\uDE84 Cluster Uptime"
                value = "${lunala.uptime}ms"
                inline = true
            }

            footer {
                icon = context.user.avatar.url
                text = "Call from ${context.user.tag}"
            }

            timestamp = Instant.now()
        }
    }

}