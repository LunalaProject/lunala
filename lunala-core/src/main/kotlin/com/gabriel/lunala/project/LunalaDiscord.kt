package com.gabriel.lunala.project

import com.gabriel.lunala.project.service.StandardPrototypeService
import com.gabriel.lunala.project.util.LunalaDiscordConfig
import com.gabriel.lunala.project.util.prepareClient
import com.gitlab.kordlib.core.Kord
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.util.*

@OptIn(KtorExperimentalAPI::class)
class LunalaDiscord(config: LunalaDiscordConfig): Lunala(config,  LunalaWrapper(config.website.url, config.website.key, HttpClient(CIO.create()) {prepareClient()})) {

    lateinit var client: Kord

    override val prototypeService = StandardPrototypeService(this)

    override suspend fun start()  {
        client = Kord(config.client.token) {
            sharding {
                0 until config.client.shards
            }
        }

        prototypeService.start()

        client.login()
    }

    override suspend fun stop() {
        TODO("Not yet implemented")
    }
}