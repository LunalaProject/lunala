package com.gabriel.lunala.project

import com.gabriel.lunala.project.achievements.AchievementHandler
import com.gabriel.lunala.project.command.registry.CommandRegistry
import com.gabriel.lunala.project.command.handler.*
import com.gabriel.lunala.project.database.LunalaDatabase
import com.gabriel.lunala.project.database.LunalaKordDatabase
import com.gabriel.lunala.project.manager.PlanetManager
import com.gabriel.lunala.project.planet.StandalonePlanet
import com.gabriel.lunala.project.table.LunalaAchievements
import com.gabriel.lunala.project.table.LunalaProfiles
import com.gabriel.lunala.project.table.LunalaServers
import com.gitlab.kordlib.core.Kord
import io.github.cdimascio.dotenv.dotenv
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.slf4j.LoggerFactory
import java.util.concurrent.Executors

class LunalaKord: Lunala() {

    override suspend fun start() {
        val logger = LoggerFactory.getLogger(Lunala::class.java)

        val client = Kord(dotenv()["TOKEN"] ?: error("Token was not defined."))
        val database = LunalaKordDatabase(this, listOf(LunalaProfiles, LunalaServers, LunalaAchievements)).also {
            it.connect()
            it.createTables()
        }


        val holder = DiscordCommandHolder()
        val handler = DiscordCommandHandler()

        val registry = CommandRegistry()

        val modules = mutableListOf(module {
            single { client }
            single { database }
            single { logger }
            single { registry }
            single<Lunala> { this@LunalaKord }
            single { AchievementHandler() }
            single { CoroutineScope(Executors.newFixedThreadPool(1).asCoroutineDispatcher()) }
            single<PlanetManager> { StandalonePlanet }
            single<CommandHandler<DiscordCommandContext>> { handler }
            single<CommandHolder> { holder }
        })

        startKoin {
            modules(modules)
        }

        registry.register(holder)
        handler.startListening()

        client.login {
            watching("test")
        }
    }

}

suspend fun main() {
    LunalaKord().start()
}