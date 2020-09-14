package com.gabriel.lunala.project.command.impl.administration

import com.gabriel.lunala.project.Lunala
import com.gabriel.lunala.project.command.Command
import com.gabriel.lunala.project.command.utils.command
import com.gabriel.lunala.project.command.handler.DiscordCommandContext
import com.gabriel.lunala.project.command.snapshot.SnapshotCommand
import com.gabriel.lunala.project.utils.flaging.Priority
import com.gabriel.lunala.project.utils.message.LunaReply
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.koin.core.inject
import kotlin.concurrent.thread

class RestartCommand: SnapshotCommand {

    private val mutex = Mutex()
    private val lunala: Lunala by inject()

    override fun create(): Command = command("restart", "rl") {
        shard<DiscordCommandContext>(priority = Priority.SEVERE) {
            reply(
                    LunaReply("\uD83D\uDEA7", "Restarting all my shards, wait a little bit...", profile),
                    LunaReply("\uD83D\uDD37", "Remind yourself that i'll stay unstable for a moment, so don't worry if I'm offline!")
            )


            restart()
        }
    }

    private suspend fun restart() = mutex.withLock(this) {
        synchronized(true) {
            thread {
                lunala.onStop()
                lunala.onStart()
            }
        }
    }

}