package com.gabriel.lunala.project.locale

import arrow.Kind
import arrow.fx.ForIO
import arrow.fx.IO
import arrow.fx.extensions.fx
import com.gabriel.lunala.project.LunalaService
import com.gabriel.lunala.project.util.adequatelyFormat
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.util.concurrent.ConcurrentHashMap

class DefaultLocaleWrapper(override val id: String): LocaleWrapper {

    override val entries: MutableMap<String, String> = mutableMapOf()

    override fun get(key: String, vararg placeholders: String): String = entries[key]?.adequatelyFormat(*placeholders) ?: "{MISSING_LOCALES}"

}

object DefaultLocaleRepository: LocaleRepository {

    override val locales: MutableMap<String, LocaleWrapper> = mutableMapOf()
    private val yaml: Yaml = Yaml()

    override fun reload(): Kind<ForIO, Unit> = IO {
        val folder = File("C:/Lunala/locales")
        locales.clear()

        folder.listFiles()?.forEach { locale ->
            if (locale.isDirectory) {
                val wrapper = DefaultLocaleWrapper(locale.name)

                locale.listFiles()?.forEach { compose ->
                    try {
                        val createdEntries = yaml.load<MutableMap<String, Any?>>(compose.readText())

                        addToLocale(wrapper, createdEntries)
                    } catch (throwable: Throwable) { return@forEach }
                }

                if (wrapper.entries.isNotEmpty()) {
                    locales[wrapper.id] = wrapper
                }
            }
        }

    }

    override fun retrieve(id: String): LocaleWrapper? =
        locales[id]

    @Suppress("unchecked_cast")
    private fun addToLocale(locale: LocaleWrapper, map: MutableMap<String, Any?>, prefix: String = ""): Unit = map.forEach { (key, value) ->
        if (value is Map<*, *>) {
            addToLocale(locale, value as MutableMap<String, Any?>, "$prefix$key.")
        } else {
            println("Transforming key ${prefix + key}")
            locale.entries[prefix + key] = value as String
        }
    }

}