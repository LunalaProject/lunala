package com.gabriel.lunala.project.config

import com.gabriel.lunala.project.environment.ClientEnvironment
import kotlinx.serialization.Serializable

@Serializable
data class LunalaDiscordConfig(
        val general: GeneralConfigSection,
        val discord: DiscordConfigSection
) {

    @Serializable
    data class GeneralConfigSection(
            val website: String,
            val repository: String,
            val environment: ClientEnvironment
    )

    @Serializable
    data class DiscordConfigSection(
            val token: String,
            val prefix: String,
            val discord: String,
            val discordId: String
    )

}