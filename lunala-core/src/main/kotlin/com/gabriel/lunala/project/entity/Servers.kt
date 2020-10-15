package com.gabriel.lunala.project.entity

import com.gabriel.lunala.project.locale.DefaultLocaleRepository
import com.gabriel.lunala.project.locale.LocaleWrapper
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object LunalaServers: LongIdTable() {

    val locale = varchar("locale", 5).default("en-us")

}

class LunalaServer(id: EntityID<Long>): LongEntity(id), Server {

    companion object: LongEntityClass<LunalaServer>(LunalaServers)

    override val idLong: Long = id.value

    private val locale: String by LunalaServers.locale

    override fun getLocale(): LocaleWrapper = DefaultLocaleRepository.retrieve(locale) ?: error("Locale $locale could not be found (call from $idLong)")

}