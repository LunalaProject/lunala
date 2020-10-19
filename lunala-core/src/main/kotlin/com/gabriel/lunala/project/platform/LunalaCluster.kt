package com.gabriel.lunala.project.platform

import arrow.fx.IO
import com.gabriel.lunala.project.util.LunalaDiscordConfig
import com.gitlab.kordlib.common.entity.Snowflake
import com.gitlab.kordlib.core.Kord
import com.gitlab.kordlib.core.supplier.EntitySupplyStrategy
import mu.KLogger

class LunalaCluster constructor(val config: LunalaDiscordConfig, val logger: KLogger) {

    lateinit var client: Kord

    val uptime = System.currentTimeMillis()
        get() = System.currentTimeMillis() - field

    fun prepare() = IO {
        logger.debug("Now preparing client, currently using ${config.client.shards} shards.")
        client = Kord(config.client.token) {
            sharding {
                0 until config.client.shards
            }
        }
    }

    fun login() = IO {
        logger.debug("Trying to log-ing, client must be prepared for this process.")
        client.login()
    }

    suspend fun getUserById(id: Snowflake, strategy: EntitySupplyStrategy<*>) = client.getUser(id, strategy)

    suspend fun getChannelById(id: Snowflake, strategy: EntitySupplyStrategy<*>) = client.getChannel(id, strategy)

    suspend fun getGuildById(id: Snowflake, strategy: EntitySupplyStrategy<*>) = client.getGuild(id, strategy)

}