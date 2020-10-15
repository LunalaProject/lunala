package com.gabriel.lunala.project.entity

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object LunalaProfiles: LongIdTable() {

    val coins = long("coins").default(0)

}

class LunalaProfile(id: EntityID<Long>): LongEntity(id), Profile {

    companion object: LongEntityClass<LunalaProfile>(LunalaProfiles)

    override val idLong: Long = id.value
    override var coins: Long by LunalaProfiles.coins

}