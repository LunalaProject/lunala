package com.gabriel.lunala.project.command.impl.misc

import com.gabriel.lunala.project.command.CommandCategory
import com.gabriel.lunala.project.command.DiscordCommandContext
import com.gabriel.lunala.project.util.*
import io.ktor.utils.io.*
import java.awt.Color
import java.time.Instant
import kotlin.time.ExperimentalTime

class PingCommand {

    fun create() = command("ping", category = CommandCategory.MISCELLANEOUS) {
        description {
            +it["commands.misc.ping.description"]
        }

        trigger {
            reply {
                append {
                    embed = getStatusEmbed()
                }
            }
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun DiscordCommandContext.getStatusEmbed() = embed {
        title = "\uD83C\uDFD3 Lunala Status"
        color = Color(0, 128, 255)
        description = locale["commands.misc.ping.description"]

        thumbnail {
            url = user.avatar.url
        }

        field {
            name = "\uD83E\uDDEA Ping"
            value = "${kord.gateway.averagePing.toLongMilliseconds()}ms"
            inline = true
        }
        field {
            name = "\uD83D\uDC8E Shards"
            value = "${kord.resources.shardCount}"
            inline = true
        }
        field {
            name = "\uD83D\uDE84 Cluster Uptime"
            value = "${cluster.uptime}ms"
            inline = true
        }

        footer {
            icon = user.avatar.url
            text = "Call from ${user.tag}"
        }

        timestamp = Instant.now()
    }

}