package com.gabriel.lunala.project.entity

import com.gabriel.lunala.project.Lunala
import com.gabriel.lunala.project.entity.Profile
import com.gabriel.lunala.project.entity.space.Planet
import com.gabriel.lunala.project.manager.PlanetManager
import com.gabriel.lunala.project.table.LunalaProfiles
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.koin.core.KoinComponent
import org.koin.core.get

class LunalaProfile(id: EntityID<Long>): LongEntity(id), Profile, KoinComponent {

    companion object: LongEntityClass<LunalaProfile>(LunalaProfiles)

    override val idLong: Long = id.value

    val oi by LunalaProfiles.currentPlanet

    override var currentPlanet: Planet
        get() = get<PlanetManager>().planets.first {
            println("Looking for ${LunalaProfiles.currentPlanet.getValue(this, LunalaProfile::currentPlanet)} but received ${it.name}")
            it.name == LunalaProfiles.currentPlanet.getValue(this, LunalaProfile::currentPlanet) }
        set(value) = LunalaProfiles.currentPlanet.setValue(this, LunalaProfile::currentPlanet, value.name)

}