package com.gabriel.lunala.project

import arrow.Kind
import arrow.fx.ForIO
import arrow.fx.IO
import arrow.fx.extensions.fx
import arrow.fx.extensions.io.applicativeError.attempt
import com.gabriel.lunala.project.util.getDecodedConfig
import mu.KLogger
import mu.KotlinLogging

class LunalaCoreService: LunalaService<ForIO> {

    override val logger: KLogger = KotlinLogging.logger {}

    override fun start(): Kind<ForIO, Unit> = IO.fx {
        val config = !getDecodedConfig()
    }

    override fun stop(): Kind<ForIO, Unit> = IO {

    }
}

suspend fun main() {
    val service: LunalaService<ForIO> = LunalaCoreService()
    service.start().attempt().suspended()
}