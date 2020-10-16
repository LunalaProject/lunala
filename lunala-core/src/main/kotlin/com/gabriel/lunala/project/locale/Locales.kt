package com.gabriel.lunala.project.locale

import arrow.Kind
import arrow.fx.ForIO
import arrow.fx.IO
import com.gabriel.lunala.project.util.LunalaExperimental
import com.gabriel.lunala.project.util.adequatelyFormat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import org.yaml.snakeyaml.Yaml
import java.io.File

class DefaultLocaleWrapper(override val id: String): LocaleWrapper {
    override val entries: MutableMap<String, String> = mutableMapOf()

    override fun get(key: String, vararg placeholders: String): String = entries[key]?.adequatelyFormat(*placeholders) ?: "{MISSING_LOCALES}"
}

@Deprecated("Workaround")
@LunalaExperimental
object DefaultLocaleRepository: LocaleRepository {

    override val locales: MutableMap<String, LocaleWrapper> = mutableMapOf()
    private val yaml: Yaml = Yaml()

    override fun reload(): Kind<ForIO, Unit> = IO {
        File("C:/Lunala/locales").listFiles()?.forEach { locale ->
            val wrapper = DefaultLocaleWrapper(locale.name)

            if (locale.isDirectory && locale.name.length == 5) {
                getEntries(locale).collect {
                    addToLocale(wrapper, it)
                }
            }
            if (wrapper.entries.isNotEmpty()) {
                locales[wrapper.id] = wrapper
            }
            System.gc()
        }
    }

    private fun getEntries(directory: File): Flow<MutableMap<String, Any?>> = flow {
        directory.listFiles()?.forEach {
            it.runCatching {
                emit(yaml.load(readText()))
            }
        }
    }

    @Suppress("unchecked_cast")
    private fun addToLocale(locale: LocaleWrapper, map: MutableMap<String, Any?>, prefix: String = ""): Unit = map.forEach { (key, value) ->
        if (value is Map<*, *>) {
            addToLocale(locale, value as MutableMap<String, Any?>, "$prefix$key.")
        } else {
            locale.entries[prefix + key] = value as String
        }
    }

    override fun retrieve(id: String): LocaleWrapper? = locales[id]
}