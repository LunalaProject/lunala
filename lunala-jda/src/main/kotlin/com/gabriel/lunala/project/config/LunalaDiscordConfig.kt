package com.gabriel.lunala.project.config

import com.gabriel.lunala.project.environment.ClientEnvironment
import kotlinx.serialization.Serializable

@Serializable
data class LunalaDiscordConfig(
        val token: String,
        val github: String,
        val website: String,
        val discord: String,
        val discordId: Long,
        val repository: String,
        val environment: ClientEnvironment,
)