package com.gabriel.lunala.project.sql

import com.gabriel.lunala.project.entity.Profile
import com.gabriel.lunala.project.utils.flaging.Priority
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.koin.core.KoinComponent

object LunalaProfiles: LongIdTable() {

    val money = long("money").default(0)
    val priority = enumeration("priority", Priority::class).default(Priority.LOW)

}

class LunalaProfile(id: EntityID<Long>): LongEntity(id), Profile, KoinComponent {

    companion object: LongEntityClass<LunalaProfile>(LunalaProfiles)

    override val idLong: Long = id.value

    override var money: Long by LunalaProfiles.money
    override var priority: Priority by LunalaProfiles.priority

}