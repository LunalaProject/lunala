package com.gabriel.lunala.project.table

import com.gabriel.lunala.project.entity.LunalaProfile
import com.gabriel.lunala.project.manager.PlanetManager
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.KoinComponent
import org.koin.core.get

object LunalaProfiles: LongIdTable(), KoinComponent {

    val currentPlanet = varchar("current_planet", 18)

    fun findOrCreate(id: Long): LunalaProfile = transaction {
        LunalaProfile.findById(id) ?: LunalaProfile.new(id) {
            currentPlanet = get<PlanetManager>().planets.first { it.default }
        }
    }

}