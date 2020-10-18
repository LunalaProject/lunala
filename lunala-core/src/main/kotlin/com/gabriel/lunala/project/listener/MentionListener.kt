package com.gabriel.lunala.project.listener

import arrow.core.Tuple2
import arrow.fx.IO
import arrow.fx.extensions.io.applicativeError.attempt
import com.gabriel.lunala.project.command.DiscordCommandHandler.Companion.PREFIX
import com.gabriel.lunala.project.platform.LunalaCluster
import com.gabriel.lunala.project.util.Listener
import com.gabriel.lunala.project.util.ProfileService
import com.gabriel.lunala.project.util.ServerService
import com.gabriel.lunala.project.util.reply
import com.gitlab.kordlib.core.event.message.MessageCreateEvent
import com.gitlab.kordlib.core.on
import kotlinx.coroutines.Job

class MentionListener(val cluster: LunalaCluster, val primary: Tuple2<ProfileService, ServerService>): Listener {

    override fun create(): IO<Job> = IO {
        cluster.client.on<MessageCreateEvent> {
            if (message.content.replace(" ", "") == "<@!${cluster.client.selfId.longValue}>" && guildId != null) {

                val profile = primary.a.findOrCreateById(message.author!!.id.longValue).suspended()
                val server = primary.b.findOrCreateById(guildId!!.longValue).suspended()

                message.channel.reply(profile) {
                    append {
                        prefix = "\uD83E\uDD73"
                        message = server.getLocale()["utils.mention", PREFIX]
                    }
                }.attempt().suspended()
            }
        }
    }

}