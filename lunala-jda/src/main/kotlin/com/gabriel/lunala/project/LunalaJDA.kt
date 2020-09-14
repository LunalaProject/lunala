package com.gabriel.lunala.project

import com.gabriel.lunala.project.command.handler.*
import com.gabriel.lunala.project.config.LunalaDiscordConfig
import com.gabriel.lunala.project.database.LunalaDatabase
import com.gabriel.lunala.project.database.LunalaDatabaseImpl
import com.gabriel.lunala.project.event.ReactiveEventManager
import com.gabriel.lunala.project.module.LunalaModuleController
import com.gabriel.lunala.project.module.StandardModuleController
import com.gabriel.lunala.project.sql.LunalaProfiles
import com.gabriel.lunala.project.sql.LunalaServers
import com.gabriel.lunala.project.sql.data.LunalaAchievements
import com.gabriel.lunala.project.utils.boot.registerCommands
import com.gabriel.lunala.project.utils.boot.registerListeners
import com.gabriel.lunala.project.utils.observer.ReactionObserver
import io.github.cdimascio.dotenv.dotenv
import kotlinx.coroutines.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder
import net.dv8tion.jda.api.sharding.ShardManager
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.inject
import org.koin.dsl.module
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import kotlin.system.exitProcess

class LunalaJDA: Lunala() {

    override fun onStart() {
        val logger = LoggerFactory.getLogger(Lunala::class.java)

        val file = File("./config.json")

        if (!file.exists()) {
            logger.info("You didn't set a config file to me, so I'm creating one so then you can configure me!")
            file.writeBytes(this::class.java.getResourceAsStream("/config.json").readAllBytes())

            exitProcess(-1)
        }

        val configuration = Json.decodeFromString<LunalaDiscordConfig>(file.readText())

        logger.info("Booting Lunala in environment ${configuration.environment.name}!")

        val job = Job()
        val scope = CoroutineScope(Dispatchers.Unconfined + job)

        val manager = DefaultShardManagerBuilder.createDefault(configuration.token).apply {
            setShardsTotal(1)
            setEventManagerProvider { ReactiveEventManager(scope) }
        }.build()

        val database: LunalaDatabase = LunalaDatabaseImpl(this, listOf(LunalaProfiles, LunalaServers, LunalaAchievements))

        val holder: CommandHolder = DiscordCommandHolder()
        val handler: CommandHandler<DiscordCommandContext> = DiscordCommandHandler()

        val observer = ReactionObserver()

        val modules = mutableListOf(module {
            single { database }
            single { configuration }
            single { handler }
            single { logger }
            single { holder }
            single { scope }
            single { observer }
            single { manager }
            single<Job> { job }
            single<Lunala> { this@LunalaJDA }
        })

        startKoin {
            modules(modules)
        }

        val controller = StandardModuleController()

        loadKoinModules(module {
            single<LunalaModuleController> { controller }
        })

        for (moduleFile in controller.files) {
            try {
                controller.load(controller.parse(moduleFile) ?: continue)
            } catch (throwable: Throwable) {}
        }

        scope.launch {
            database.connect()
            database.createTables()
        }

        registerCommands()
        registerListeners()
    }

    override fun onStop() {
        val logger: Logger by inject()

        val config: LunalaDiscordConfig by inject()
        logger.info("Received shutdown request to environment ${config.environment.name}.")

        val job: Job by inject()
        job.cancel()

        logger.info("Disabling discord client")

        val holder: CommandHolder by inject()
        holder.commands.clear()

        val manager: ShardManager by inject()
        manager.setStatus(OnlineStatus.OFFLINE)
        manager.shutdown()

        stopKoin()
    }

}

fun main() {
    LunalaJDA().onStart()
}