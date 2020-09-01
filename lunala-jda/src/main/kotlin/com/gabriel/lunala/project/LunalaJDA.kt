package com.gabriel.lunala.project

import com.gabriel.lunala.project.command.handler.*
import com.gabriel.lunala.project.command.registry.CommandRegistry
import com.gabriel.lunala.project.database.LunalaDatabase
import com.gabriel.lunala.project.database.LunalaDatabaseImpl
import com.gabriel.lunala.project.event.ReactiveEventManager
import com.gabriel.lunala.project.table.LunalaAchievements
import com.gabriel.lunala.project.table.LunalaProfiles
import com.gabriel.lunala.project.table.LunalaServers
import io.github.cdimascio.dotenv.dotenv
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.slf4j.LoggerFactory

class LunalaJDA: Lunala() {

    override suspend fun start() {
        val logger = LoggerFactory.getLogger(Lunala::class.java)

        val job = Job()
        val scope = CoroutineScope(Dispatchers.Unconfined + job)

        val manager = DefaultShardManagerBuilder.createDefault(dotenv()["TOKEN"]).apply {
            setShardsTotal(1)
            setEventManagerProvider { ReactiveEventManager(scope) }
        }.build()

        val database: LunalaDatabase = LunalaDatabaseImpl(this, listOf(LunalaProfiles, LunalaServers, LunalaAchievements)).also {
            it.connect()
        }

        val holder: CommandHolder = DiscordCommandHolder()
        val handler = DiscordCommandHandler(manager)

        val registry = CommandRegistry()

        val modules = mutableListOf(module {
            single { database }
            single { logger }
            single { registry }
            single { holder }
            single { scope }
            single { manager }
            single<Job> { job }
            single<Lunala> { this@LunalaJDA }
        })

        startKoin {
            modules(modules)
        }

        database.createTables()
        registry.register(holder)
    }

}

suspend fun main() {
    LunalaJDA().start()
}