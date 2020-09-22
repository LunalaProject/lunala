package com.gabriel.lunala.project.commands

import com.gabriel.lunala.project.Lunala
import com.gabriel.lunala.project.LunalaGuildManager
import com.gabriel.lunala.project.command.Command
import com.gabriel.lunala.project.command.snapshot.SnapshotCommand
import com.gabriel.lunala.project.command.utils.command
import com.gabriel.lunala.project.config.LunalaDiscordConfig
import com.gabriel.lunala.project.utils.local
import com.gabriel.lunala.project.utils.modules.unregisterCommands
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import org.koin.core.inject
import java.awt.AlphaComposite
import java.awt.Font
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import javax.imageio.ImageIO

class SimpleCommand(private val module: LunalaGuildManager): SnapshotCommand {

    private val config: LunalaDiscordConfig by inject()
    private val scope: CoroutineScope by inject()

    private val file = File("test.png")
    private val font = File(config.general.repository + "/fonts/Daddy Day.ttf").let { if (it.exists()) it else null }

    override fun create(): Command = command("simple") {
        local {
            if (font == null) return@local println("deu ruim :(")

            val image: BufferedImage = scope.async(Dispatchers.IO) {
                ImageIO.read(file)
            }.await()

            val font: Font = scope.async(Dispatchers.IO) {
                Font.createFont(Font.TRUETYPE_FONT, font).deriveFont(280F)
            }.await()

            val graphics = image.createGraphics().also {
                it.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
                it.composite = AlphaComposite.Src

                it.font = font
            }
            graphics.drawString("SIMPLE", 550, 500)

            val output: ByteArrayOutputStream = object : ByteArrayOutputStream() {
                @Synchronized
                override fun toByteArray(): ByteArray {
                    return buf
                }
            }

            withContext(Dispatchers.IO){
                ImageIO.write(image, "png", output)
            }

            channel.sendMessage("Test").addFile(ByteArrayInputStream(output.toByteArray(), 0, output.size()), "sample.png").queue()
        }
    }
}