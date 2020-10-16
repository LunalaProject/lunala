package com.gabriel.lunala.project.locale

import arrow.Kind
import arrow.fx.ForIO
import arrow.fx.IO
import com.gabriel.lunala.project.util.LunalaExperimental
import kotlinx.coroutines.CoroutineScope

interface LocaleWrapper {

    val id: String
    val entries: MutableMap<String, String>

    operator fun get(key: String, vararg placeholders: String): String

}

interface LocaleRepository {

    val locales: MutableMap<String, LocaleWrapper>

    fun reload(): Kind<ForIO, Unit>

    fun retrieve(id: String): LocaleWrapper?

}