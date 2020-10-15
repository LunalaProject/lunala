package com.gabriel.lunala.project.util

import arrow.fx.IO
import arrow.fx.extensions.fx
import com.gabriel.lunala.project.SingleLunalaService
import com.typesafe.config.ConfigFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.hocon.Hocon
import kotlinx.serialization.hocon.decodeFromConfig

@OptIn(ExperimentalSerializationApi::class)
suspend fun main() {
    val configIo: IO<LunalaDiscordConfig> = inspect("config.conf").map {
        Hocon.decodeFromConfig(ConfigFactory.parseFile(it))
    }

    IO.fx {
        SingleLunalaService(configIo.not()).start().attempt().not()
    }.attempt().suspended()
}