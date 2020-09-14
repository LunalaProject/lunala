package com.gabriel.lunala.project.sql

import com.gabriel.lunala.project.entity.extra.Planet
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.koin.core.KoinComponent

object LunalaPlanets: IdTable<String>() {

    override val id: Column<EntityID<String>> = varchar("name", 36).entityId()

}

class LunalaPlanet(id: EntityID<String>): Entity<String>(id), Planet, KoinComponent {

    companion object: EntityClass<String, LunalaPlanet>(LunalaPlanets)

    override val name: String = id.value

}