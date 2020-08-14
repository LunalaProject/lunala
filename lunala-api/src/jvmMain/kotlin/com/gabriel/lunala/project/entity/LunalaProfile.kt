package com.gabriel.lunala.project.entity

import com.gabriel.lunala.project.Lunala
import com.gabriel.lunala.project.database.entity.Profile
import com.gabriel.lunala.project.table.LunalaProfiles
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.koin.core.get

class LunalaProfile(id: EntityID<Long>): LongEntity(id), Profile {

    companion object: LongEntityClass<LunalaProfile>(LunalaProfiles)

    override val idLong: Long = id.value

}