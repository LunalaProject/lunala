package com.gabriel.lunala.project.entity

import com.gabriel.lunala.project.entity.extra.Planet
import com.gabriel.lunala.project.manager.PlanetManager
import com.gabriel.lunala.project.table.LunalaProfiles
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.koin.core.KoinComponent
import org.koin.core.get

class LunalaProfile(id: EntityID<Long>): LongEntity(id), Profile, KoinComponent {

    companion object: LongEntityClass<LunalaProfile>(LunalaProfiles)

    override val idLong: Long = id.value

    override var money: Long by LunalaProfiles.money

}