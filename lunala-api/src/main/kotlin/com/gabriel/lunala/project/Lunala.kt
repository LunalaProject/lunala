package com.gabriel.lunala.project

import com.gabriel.lunala.project.entity.Guild
import com.gabriel.lunala.project.entity.User
import com.gabriel.lunala.project.service.GuildService
import com.gabriel.lunala.project.service.PlanetService
import com.gabriel.lunala.project.service.PrototypeService
import com.gabriel.lunala.project.service.UserService
import com.gabriel.lunala.project.util.LunalaDiscordConfig
import com.gabriel.lunala.project.util.LunalaGuild
import com.gabriel.lunala.project.util.LunalaProfile
import com.gabriel.lunala.project.util.default
import com.gabriel.lunala.project.util.kotlin.valueOrNull

/**
 * This is a Lunala's cluster instance
 */
abstract class Lunala(val config: LunalaDiscordConfig, val wrapper: LunalaWrapper) {

    val userService = UserService(wrapper)
    val guildService = GuildService(wrapper)
    val planetService = PlanetService(wrapper)

    abstract val prototypeService: PrototypeService

    val uptime = System.currentTimeMillis()
        get() = System.currentTimeMillis() - field

    abstract suspend fun start()

    abstract suspend fun stop()

    suspend fun getProfileById(id: Long, createIfNull: Boolean = false): LunalaProfile? = valueOrNull {
        userService.retrieve(id) ?: if (createIfNull) userService.create(User.default(id)) else null
    }

    suspend fun getServerById(id: Long, createIfNull: Boolean = false): LunalaGuild? = valueOrNull {
        guildService.retrieve(id) ?: if (createIfNull) guildService.create(Guild.default(id)) else null
    }
}