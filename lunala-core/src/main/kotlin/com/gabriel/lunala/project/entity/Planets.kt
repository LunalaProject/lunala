package com.gabriel.lunala.project.entity

import com.gabriel.lunala.project.util.GalaxyService
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object LunalaPlanets: IdTable<String>() {

    override val id: Column<EntityID<String>> = varchar("name", 32).entityId()

    var distance = long("distance")
    var budget = long("budget")
    var security = float("security")
    var owner = long("owner")
    val galaxy = varchar("galaxy", 16)
    var visited = bool("visited")

}

class LunalaPlanet(id: EntityID<String>): Entity<String>(id), Planet {

    companion object: EntityClass<String, LunalaPlanet>(LunalaPlanets)

    override val name: String = id.value

    override var distance: Long by LunalaPlanets.distance
    override var budget: Long by LunalaPlanets.budget
    override var security: Float by LunalaPlanets.security
    override var owner: Long by LunalaPlanets.owner
    override var galaxy: String by LunalaProfiles.galaxy
    override var visited: Boolean by LunalaPlanets.visited

    override suspend fun getGalaxy(service: GalaxyService): Galaxy =
            service.findById(galaxy).suspended()


}