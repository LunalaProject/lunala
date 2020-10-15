package com.gabriel.lunala.project.util

import kotlinx.serialization.Serializable

@Serializable
data class LunalaDiscordConfig(
    val general: GeneralConfigSection,
    val discord: DiscordConfigSection,
    val client: ClientConfigSection,
    val database: DatabaseConfigSection
) {

    @Serializable
    data class GeneralConfigSection(
        val website: String,
        val environment: String
    )

    @Serializable
    data class DiscordConfigSection(
        val discord: String,
        val discordId: String
    )

    @Serializable
    data class ClientConfigSection(
        val token: String,
        val shards: Int
    )

    @Serializable
    data class DatabaseConfigSection(
        val host: String,
        val port: String,
        val database: String,
        val user: String,
        val password: String
    )

}

class ConfigInspectionException(message: String?, throwable: Throwable? = null): Exception(message, throwable)