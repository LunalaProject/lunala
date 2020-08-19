package com.gabriel.lunala.project

import com.gabriel.lunala.project.achievements.AchievementHandler
import com.gabriel.lunala.project.command.flow.CommandRegistry
import com.gabriel.lunala.project.command.handler.*
import com.gabriel.lunala.project.database.LunalaDatabase
import com.gabriel.lunala.project.manager.PlanetManager
import com.gabriel.lunala.project.planet.Planets
import com.gabriel.lunala.project.table.LunalaAchievements
import com.gabriel.lunala.project.table.LunalaProfiles
import com.gabriel.lunala.project.table.LunalaServers
import com.gabriel.lunala.project.utils.text.LevenshteinCalculator
import com.gitlab.kordlib.core.Kord
import io.github.cdimascio.dotenv.dotenv
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
        val database = LunalaDatabase(listOf(LunalaProfiles, LunalaServers, LunalaAchievements)).also {
            it.connect()
            it.createTables()
        }

        val holder = DiscordCommandHolder()
        val handler = DiscordCommandHandler()

        val flowRegistry = CommandRegistry()

        val modules = mutableListOf(module {
            single { client }
            single { database }
            single { logger }
            single { flowRegistry }
            single<AchievementHandler> { AchievementHandler() }
            single { CoroutineScope(Executors.newFixedThreadPool(1).asCoroutineDispatcher()) }
            single<PlanetManager> { Planets }
            single<CommandHandler<DiscordCommandContext>> { handler }
            single<CommandHolder> { holder }
        })

        startKoin {
            modules(modules)
        }

        flowRegistry.register(holder)
        handler.startListening()

        client.login {
            watching("test")
        }
    }

}

suspend fun main() {
    LunalaKord().start()
}