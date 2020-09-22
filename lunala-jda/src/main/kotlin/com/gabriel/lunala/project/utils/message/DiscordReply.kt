package com.gabriel.lunala.project.utils.message

import com.gabriel.lunala.project.image.Image
import com.gabriel.lunala.project.utils.Mentionable
import com.gabriel.lunala.project.utils.embed.EmbedBuilderDSL
import com.gabriel.lunala.project.utils.embed.embed
import net.dv8tion.jda.api.entities.MessageEmbed

class DiscordReply(
        prefix: String = ":large_blue_diamond:",
        content: String = "",
        mentionable: Mentionable? = null,
        image: Image? = null,
        val embed: MessageEmbed? = null
): LunaReply(prefix, content, mentionable, image) {

    companion object {

        inline operator fun invoke(mentionable: Mentionable? = null, dsl: EmbedBuilderDSL.() -> Unit): DiscordReply =
                DiscordReply(mentionable = mentionable, embed = EmbedBuilderDSL().apply(dsl).build())

    }

}
