package com.gabriel.lunala.project

import arrow.Kind
import arrow.fx.ForIO
import arrow.fx.IO
import com.gabriel.lunala.project.util.LunalaDiscordConfig
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class SingleLunalaService(override val config: LunalaDiscordConfig): LunalaService<ForIO> {

    private val logger: Logger = LoggerFactory.getLogger(LunalaService::class.java)

    override fun start(): Kind<ForIO, Unit> = IO {

    }

    override fun stop(): Kind<ForIO, Unit> = IO {

    }
}
