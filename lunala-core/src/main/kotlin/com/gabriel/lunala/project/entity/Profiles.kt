package com.gabriel.lunala.project.entity

import com.gabriel.lunala.project.util.Service
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object LunalaProfiles: LongIdTable() {

    val ship = integer("ship").default(1)
    val coins = long("coins").default(500)
    val equipment = integer("equipment").default(1)
    val crew = integer("crew")
    val planet = varchar("planet", 16).default("Earth")
    val galaxy = varchar("galaxy", 16).default("Milky way")

}

class LunalaProfile(id: EntityID<Long>): LongEntity(id), Profile {

    companion object: LongEntityClass<LunalaProfile>(LunalaProfiles)

    override val idLong: Long = id.value

    override var ship: Int by LunalaProfiles.ship
    override var coins: Long by LunalaProfiles.coins
    override var equipment: Int by LunalaProfiles.equipment
    override var crew: Int by LunalaProfiles.crew
    override var planet: String by LunalaProfiles.planet
    override var galaxy: String by LunalaProfiles.galaxy

    override suspend fun getPlanet(service: Service<String, Planet>): Planet =
            service.findById(planet).suspended()
}