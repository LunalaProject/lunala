package com.gabriel.lunala.project.service

import arrow.Kind
import arrow.core.Tuple2
import arrow.fx.ForIO
import arrow.fx.IO
import com.gabriel.lunala.project.command.*
import com.gabriel.lunala.project.command.impl.PingCommand
import com.gabriel.lunala.project.platform.LunalaCluster
import com.gabriel.lunala.project.util.ProfileService
import com.gabriel.lunala.project.util.ServerService
import kotlinx.coroutines.Job

class DefaultCommandService(private val cluster: LunalaCluster, private val services: Tuple2<ProfileService, ServerService>) {

    private var commandsJob: Job? = null

    fun start(): Kind<ForIO, Unit> = IO {
        registerAll()
        commandsJob = DiscordCommandHandler(cluster, services).setup()
    }

    private fun registerAll() {
        PingCommand().setup()
    }

    fun stop(): Kind<ForIO, Unit> = IO {
        commandsJob?.cancel()
    }

}