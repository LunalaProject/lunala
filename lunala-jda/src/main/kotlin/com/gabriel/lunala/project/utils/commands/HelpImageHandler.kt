package com.gabriel.lunala.project.utils.commands

import com.gabriel.lunala.project.command.Command
import com.gabriel.lunala.project.command.CommandContext
import com.gabriel.lunala.project.command.handler.DiscordCommandContext
import com.gabriel.lunala.project.config.LunalaDiscordConfig
import com.gabriel.lunala.project.utils.embed.embed
import com.gabriel.lunala.project.utils.extensions.fileOrNull
import com.gabriel.lunala.project.utils.message.DiscordReply
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.TextChannel
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.awt.AlphaComposite
import java.awt.Color
import java.awt.Font
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import javax.imageio.ImageIO

object HelpImageHandler: KoinComponent {

    private val config: LunalaDiscordConfig by inject()
    private val scope: CoroutineScope by inject()

    private val file = File(config.general.repository + "/assets/commands/help.png").fileOrNull()
    private val font = File(config.general.repository + "/fonts/Daddy Day.ttf").fileOrNull()

    suspend fun send(context: DiscordCommandContext) = with<DiscordCommandContext, Unit>(context) {
        if (font == null || file == null) println("Missing assets for displaying help image, going to ignore request.")

        val image: BufferedImage = scope.async(Dispatchers.IO) {
            ImageIO.read(file)
        }.await()

        val font: Font = scope.async(Dispatchers.IO) {
            Font.createFont(Font.TRUETYPE_FONT, font).deriveFont(245F)
        }.await()

        val graphics = image.createGraphics().also {
            it.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
            it.composite = AlphaComposite.Src

            it.font = font
        }
        graphics.drawString(command.labels[0], 500, 500)

        val output: ByteArrayOutputStream = object : ByteArrayOutputStream() {
            @Synchronized
            override fun toByteArray(): ByteArray = buf
        }

        withContext(Dispatchers.IO){
            ImageIO.write(image, "png", output)
        }

        val embed = embed {
            header {
                title = "\uD83E\uDE90 About Command"
                description = ""
            }
            fieldset {
                field {
                    name = "\uD83D\uDD8C Names"
                    value = "_${context.command.labels.joinToString(", ")}_"
                }
                field {
                    name = "\uD83C\uDF93 Description"
                    value = context.command.description
                }
                field {
                    name = "\uD83C\uDFEB Example"
                    value = "`${context.command.example}`"
                }
            }
            images {
                color = Color(183, 3, 183)
                this.image = "attachment://sample.png"
                thumbnail = context.client.selfUser.effectiveAvatarUrl
            }
            footer {
                text = "Lunala • ${context.member.user.asTag}"
                icon = context.member.user.effectiveAvatarUrl
                timestamp = now()
            }
        }

        channel.sendMessage(embed).addFile(ByteArrayInputStream(output.toByteArray(), 0, output.size()), "sample.png").queue()
    }

}
