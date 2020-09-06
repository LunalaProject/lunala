package com.gabriel.lunala.project

import com.gabriel.lunala.project.command.handler.*
import com.gabriel.lunala.project.database.LunalaDatabase
import com.gabriel.lunala.project.database.LunalaDatabaseImpl
import com.gabriel.lunala.project.environment.ClientEnvironment
import com.gabriel.lunala.project.event.ReactiveEventManager
import com.gabriel.lunala.project.sql.LunalaProfiles
import com.gabriel.lunala.project.sql.LunalaServers
import com.gabriel.lunala.project.sql.data.LunalaAchievements
import com.gabriel.lunala.project.utils.boot.registerCommands
import com.gabriel.lunala.project.utils.boot.registerListeners
import io.github.cdimascio.dotenv.dotenv
import kotlinx.coroutines.*
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder
import net.dv8tion.jda.api.sharding.ShardManager
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.inject
import org.koin.dsl.module
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class LunalaJDA(
        environment: ClientEnvironment
): Lunala(environment) {

    override fun start() {
        val logger = LoggerFactory.getLogger(Lunala::class.java)

        val job = Job()
        val scope = CoroutineScope(Dispatchers.Unconfined + job)

        val manager = DefaultShardManagerBuilder.createDefault(dotenv()["TOKEN"]).apply {
            setShardsTotal(1)
            setEventManagerProvider { ReactiveEventManager(scope) }
        }.build()

        val database: LunalaDatabase = LunalaDatabaseImpl(this, listOf(LunalaProfiles, LunalaServers, LunalaAchievements))

        val holder: CommandHolder = DiscordCommandHolder()
        val handler: CommandHandler<DiscordCommandContext> = DiscordCommandHandler()

        val modules = mutableListOf(module {
            single { database }
            single { handler }
            single { logger }
            single { holder }
            single { scope }
            single { manager }
            single<Job> { job }
            single<Lunala> { this@LunalaJDA }
        })

        startKoin {
            modules(modules)
        }

        scope.launch {
            database.connect()
            database.createTables()
        }

        registerCommands()
        registerListeners()
    }

    override fun stop() {
        val job: Job by inject()
        val logger: Logger by inject()
        val manager: ShardManager by inject()

        logger.info("Received shutdown request to environment ${environment.name}.")

        logger.info("Cancelling parent job.")

        job.cancel()

        logger.info("Disabling discord client")

        manager.setStatus(OnlineStatus.OFFLINE)
        manager.shutdown()

        stopKoin()
    }

}

fun main() {
    LunalaJDA(ClientEnvironment.PRODUCTION).start()
}