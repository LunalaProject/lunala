package com.gabriel.lunala.project.entity

import com.gabriel.lunala.project.util.PlanetService
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object LunalaGalaxies: IdTable<String>() {

    override val id: Column<EntityID<String>> = LunalaPlanets.varchar("name", 16).entityId()

    val default = varchar("defaultPlanet", 32)
}

class LunalaGalaxy(id: EntityID<String>): Entity<String>(id), Galaxy {

    companion object: EntityClass<String, LunalaGalaxy>(LunalaGalaxies)

    override val name: String = id.value
    override val defaultPlanet: String by LunalaGalaxies.default

    override suspend fun getDefaultPlanet(service: PlanetService): Planet =
            service.findById(defaultPlanet).suspended()
}
