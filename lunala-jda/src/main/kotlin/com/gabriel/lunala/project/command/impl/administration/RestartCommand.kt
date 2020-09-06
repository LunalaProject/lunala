package com.gabriel.lunala.project.command.impl.administration

import com.gabriel.lunala.project.Lunala
import com.gabriel.lunala.project.command.Command
import com.gabriel.lunala.project.command.api.command
import com.gabriel.lunala.project.command.handler.DiscordCommandContext
import com.gabriel.lunala.project.command.snapshot.SnapshotCommand
import com.gabriel.lunala.project.utils.flaging.Flag
import com.gabriel.lunala.project.utils.message.LunaReply
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import net.dv8tion.jda.api.entities.Activity
import org.koin.core.inject
import java.util.logging.Handler

class RestartCommand: SnapshotCommand {

    private val mutex = Mutex()
    private val lunala: Lunala by inject()

    override fun create(): Command = command("restart", "rl") {
        shard<DiscordCommandContext>(flag = Flag.SEVERE) {
            reply(
                    LunaReply("\uD83D\uDEA7", "Reiniciando todos as minhas shards, sugiro que aguarde um pouco...", profile),
                    LunaReply("\uD83D\uDD35", "Lembre-se que vou ficar instável por um momento, então não se preocupe se eu ficar offline!")
            )

            restart()
        }
    }

    private suspend fun restart() = mutex.withLock(this) {
        lunala.stop()
        lunala.start()
    }

}