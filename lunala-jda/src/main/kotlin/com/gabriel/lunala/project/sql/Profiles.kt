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
    val level = integer("level").default(1)
    val priority = enumeration("priority", Priority::class).default(Priority.LOW)
    val tripulation = integer("tripulation").default(3)
    val travelingTime = long("traveling_time").nullable().also { it.defaultValueFun = { null } }

}

class LunalaProfile(id: EntityID<Long>): LongEntity(id), Profile, KoinComponent {

    companion object: LongEntityClass<LunalaProfile>(LunalaProfiles)

    override val idLong: Long = id.value

    override var level: Int by LunalaProfiles.level
    override var money: Long by LunalaProfiles.money
    override var priority: Priority by LunalaProfiles.priority
    override var tripulation: Int by LunalaProfiles.tripulation
    override var travelingTime: Long? by LunalaProfiles.travelingTime

}