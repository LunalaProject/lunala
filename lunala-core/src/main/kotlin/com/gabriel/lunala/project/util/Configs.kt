package com.gabriel.lunala.project.util

import arrow.fx.IO
import arrow.fx.extensions.fx
import com.gabriel.lunala.project.LunalaService
import com.typesafe.config.ConfigFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.hocon.Hocon
import kotlinx.serialization.hocon.decodeFromConfig
import java.io.File

@OptIn(ExperimentalSerializationApi::class)
fun <A> LunalaService<A>.getDecodedConfig(): IO<LunalaDiscordConfig> = inspectConfig().map {
    Hocon.decodeFromConfig(ConfigFactory.parseFile(it))
}

fun <A> LunalaService<A>.inspectConfig(): IO<File> = IO.fx {
    val file = File("config.conf")

    if (file.exists().not()) {
        logger.info { "Config file was not found! Killing process and creating one." }

        file.createNewFile()
        file.writeBytes(LunalaService::class.java.getResourceAsStream("/config.conf").readAllBytes())

        throw ConfigInspectionException("Config file not found")
    }

    file
}