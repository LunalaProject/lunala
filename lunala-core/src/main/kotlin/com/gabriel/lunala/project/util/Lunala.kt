package com.gabriel.lunala.project.util

import com.gabriel.lunala.project.Lunala
import com.gabriel.lunala.project.LunalaDiscord
import com.typesafe.config.ConfigFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.hocon.Hocon
import kotlinx.serialization.hocon.decodeFromConfig

@Deprecated("May be replaced")
lateinit var lunala: Lunala

suspend fun main() {
    val config = loadConfig()

    lunala = LunalaDiscord(config).apply {
        start()
    }
}

@OptIn(ExperimentalSerializationApi::class)
private fun loadConfig(): LunalaDiscordConfig = inspect("config.conf").let {
    Hocon.decodeFromConfig<LunalaDiscordConfig>(ConfigFactory.parseFile(it))
}