package com.gabriel.lunala.project.command.impl.misc

import arrow.fx.IO
import arrow.fx.extensions.fx
import com.gabriel.lunala.project.command.CommandCategory
import com.gabriel.lunala.project.command.DiscordCommandContext
import com.gabriel.lunala.project.util.SafeMessage
import com.gabriel.lunala.project.util.command
import com.gabriel.lunala.project.util.toBuilder
import com.gitlab.kordlib.core.behavior.channel.createMessage
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class SayCommand(private val json: Json = Json { ignoreUnknownKeys = true }) {

    fun create() = command("say", category = CommandCategory.MISCELLANEOUS) {
        description {
            + it["commands.miscellaneous.say.description"]
        }

        trigger {
            val message = getMessage().attempt().suspended().orNull() ?: args.joinToString(" ")

            if (message is SafeMessage) {
                channel.createMessage {
                    content = message.content
                    embed = message.embed?.toBuilder()
                }
            } else {
                channel.createMessage(message as String)
            }
        }
    }

    private fun DiscordCommandContext.getMessage(): IO<SafeMessage> = IO {
        json.decodeFromString(args.joinToString(" "))
    }

}