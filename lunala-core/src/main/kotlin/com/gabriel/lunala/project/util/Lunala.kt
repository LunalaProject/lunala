package com.gabriel.lunala.project.util

import arrow.fx.IO
import arrow.fx.extensions.fx
import arrow.fx.extensions.io.monad.monad
import com.gabriel.lunala.project.SingleLunalaService
import com.typesafe.config.ConfigFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.hocon.Hocon
import kotlinx.serialization.hocon.decodeFromConfig

suspend fun main(): Unit = IO.fx {
    val config = !effect { loadConfig() }

    val lunalaService = !SingleLunalaService(config, IO.monad())
        .start()
        .attempt()
}.suspended()

@OptIn(ExperimentalSerializationApi::class)
private suspend fun loadConfig(): LunalaDiscordConfig = inspect("config.conf").map {
    Hocon.decodeFromConfig<LunalaDiscordConfig>(ConfigFactory.parseFile(it))
}.suspended()