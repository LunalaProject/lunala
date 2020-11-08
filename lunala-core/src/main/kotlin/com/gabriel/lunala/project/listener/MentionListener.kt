package com.gabriel.lunala.project.listener

import com.gabriel.lunala.project.LunalaDiscord
import com.gabriel.lunala.project.command.DiscordCommandHandler
import com.gabriel.lunala.project.util.command.reply
import com.gitlab.kordlib.core.event.message.MessageCreateEvent
import com.gitlab.kordlib.core.on
import kotlinx.coroutines.Job

class MentionListener(val lunala: LunalaDiscord) {

    fun create(): Job = lunala.client.on<MessageCreateEvent> {
        if (message.content.replace(" ", "") == "<@!${lunala.client.selfId.longValue}>" && guildId != null) {
            val profile = lunala.getProfileById(message.author!!.id.longValue, createIfNull = true)
            val botPrefix = DiscordCommandHandler.PREFIX

            message.channel.reply(profile) {
                append {
                    prefix = "\uD83E\uDD73"
                    message = "Hey, I'm Lunala! A lightweight space exploration bot! My prefix on this server is `%s`!".format(botPrefix)
                    mentions = true
                }
            }
        }
    }

}