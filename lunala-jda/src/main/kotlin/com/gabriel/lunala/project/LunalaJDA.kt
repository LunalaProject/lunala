package com.gabriel.lunala.project

import com.gabriel.lunala.project.command.handler.*
import com.gabriel.lunala.project.config.LunalaDiscordConfig
import com.gabriel.lunala.project.database.LunalaDatabase
import com.gabriel.lunala.project.database.LunalaDatabaseImpl
import com.gabriel.lunala.project.event.ReactiveEventManager
import com.gabriel.lunala.project.module.LunalaModuleController
import com.gabriel.lunala.project.module.DiscordModuleController
import com.gabriel.lunala.project.sql.LunalaProfiles
import com.gabriel.lunala.project.sql.LunalaServers
import com.gabriel.lunala.project.sql.data.LunalaAchievements
import com.gabriel.lunala.project.utils.boot.registerCommands
import com.gabriel.lunala.project.utils.boot.registerListeners
import com.gabriel.lunala.project.utils.observer.ReactionObserver
import com.typesafe.config.ConfigFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.hocon.Hocon
import kotlinx.serialization.hocon.decodeFromConfig
import kotlinx.serialization.json.Json
import net.dv8tion.jda.api.OnlineStatus
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
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.system.exitProcess

class LunalaJDA: Lunala() {

    @ExperimentalSerializationApi
    override fun onStart() {
        val logger = LoggerFactory.getLogger(Lunala::class.java)

        val file = File("./config.conf")

        if (!file.exists()) {
            logger.info("You didn't set a config file to me, so I'm creating one!")
            file.writeBytes(this::class.java.getResourceAsStream("/config.conf").readAllBytes())

            exitProcess(-1)
        }

        val configuration = Hocon.decodeFromConfig<LunalaDiscordConfig>(ConfigFactory.parseFile(file))

        logger.info("Booting Lunala in environment ${configuration.general.environment.name}!")

        logger.info("Booting with configs: ${configuration}")

        val job = Job()
        val scope = CoroutineScope(Dispatchers.Unconfined + job)

        val manager = DefaultShardManagerBuilder.createDefault(configuration.discord.token).apply {
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

        val controller = DiscordModuleController()

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
        logger.info("Received shutdown request to environment ${config.general.environment.name}.")

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