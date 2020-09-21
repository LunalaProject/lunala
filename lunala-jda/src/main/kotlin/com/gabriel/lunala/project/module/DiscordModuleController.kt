package com.gabriel.lunala.project.module

import com.gabriel.lunala.project.Lunala
import com.gabriel.lunala.project.command.handler.CommandHolder
import com.gabriel.lunala.project.config.LunalaDiscordConfig
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.dv8tion.jda.api.hooks.IEventManager
import net.dv8tion.jda.api.sharding.ShardManager
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.inject
import org.slf4j.Logger
import java.io.File
import java.net.URL
import java.net.URLClassLoader
import java.util.jar.JarFile

// Heavy Reference on Loritta's Plugin System
class DiscordModuleController: LunalaModuleController, KoinComponent {

    private val lunala: Lunala by inject()
    private val logger: Logger by inject()

    private val holder: CommandHolder by inject()
    private val manager: IEventManager = get<ShardManager>().shards[0].eventManager

    private val config: LunalaDiscordConfig by inject()

    val modules: MutableMap<String, LunalaModule> = mutableMapOf()
    val moduleFiles: MutableMap<String, File> = mutableMapOf()
    val files = File(config.repository + "/modules").also { it.mkdirs() }.listFiles()!!

    override fun load(module: LunalaModule) {
        logger.info("Now loading module ${module.name} at environment ${config.environment.name}")

        modules[module.name] = module

        if (module is DiscordModule && module.file != null) moduleFiles[module.name] = module.file!!

        runCatching {
            module.onStart()
        }.onSuccess {
            module.commands.forEach {
                holder.commands[it.labels] = it
            }

            module.listeners.forEach {
                manager.register(it)
            }

            module.enabled = true
            logger.info("Success enabled module ${module.name}")
        }.onFailure {
            module.enabled = false
            logger.info("Error while enabling module ${module.name}: ${it::class.simpleName}: ${it.message}"); unload(module)
        }
    }

    override fun parse(name: String?): LunalaModule? {
        return parse(moduleFiles[name] ?: return null)
    }

    fun parse(file: File) = runCatching {
        val jarFile = JarFile(file)

        val description = Json.decodeFromString<ModuleInfo>(jarFile.getInputStream(jarFile.getJarEntry("module.json")).bufferedReader().readText())

        val loader = URLClassLoader.newInstance(arrayOf<URL>(file.toURI().toURL()))
        val url = file.toURI().toURL()

        URLClassLoader::class.java.getDeclaredMethod("addURL", URL::class.java).run {
            isAccessible = true
            invoke(loader, url)
        }

        val klass = Class.forName(description.primary, true, loader)

        return@runCatching (klass.getConstructor(String::class.java, File::class.java).newInstance(description.name, file) as LunalaModule)
    }.onFailure { it.printStackTrace() }.getOrNull()

    override fun unload(module: LunalaModule) {
        logger.info("Now disabling module ${module.name} at ${config.environment.name}")

        module.enabled = false

        runCatching {
            module.onStop()
            module.tasks.forEach {
                it.cancel()
            }
        }.onSuccess {
            module.commands.forEach {
                holder.commands.remove(it.labels, it)
            }
            module.listeners.forEach {
                manager.unregister(it)
            }
        }
    }

    @Serializable
    private data class ModuleInfo(val name: String, val primary: String)

}