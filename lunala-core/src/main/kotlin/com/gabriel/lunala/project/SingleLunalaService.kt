package com.gabriel.lunala.project

import arrow.Kind
import arrow.core.toT
import arrow.core.toTuple3
import arrow.fx.ForIO
import arrow.fx.IO
import arrow.fx.extensions.fx
import arrow.typeclasses.Monad
import com.gabriel.lunala.project.locale.DefaultLocaleRepository
import com.gabriel.lunala.project.platform.LunalaCluster
import com.gabriel.lunala.project.service.*
import com.gabriel.lunala.project.util.LunalaDiscordConfig
import com.gabriel.lunala.project.util.setupListeners
import mu.KLogger
import mu.toKLogger
import org.slf4j.LoggerFactory

class SingleLunalaService<F>(override val config: LunalaDiscordConfig, MF: Monad<F>): LunalaService<ForIO>, Monad<F> by MF {

    private val logger: KLogger = LoggerFactory.getLogger(LunalaService::class.java).toKLogger()
    private val cluster: LunalaCluster = LunalaCluster(config, logger)

    override fun start(): Kind<ForIO, Unit> = IO.fx {
        cluster.prepare().bind()

        val primaryServices = DefaultProfileService() toT DefaultServerService()
        val planetsService = DefaultPlanetService()

        DefaultLocaleRepository.reload().bind()
        DefaultDatabaseService(config).let {
            it.connect().bind()
            it.createTables().bind()
        }

        DefaultCommandService(cluster, Triple(primaryServices.a, primaryServices.b, planetsService).toTuple3()).start().bind()
        setupListeners(cluster, primaryServices)

        cluster.login().bind()
    }

    override fun stop(): Kind<ForIO, Unit> = IO {
        cluster.client.shutdown()
    }
}
