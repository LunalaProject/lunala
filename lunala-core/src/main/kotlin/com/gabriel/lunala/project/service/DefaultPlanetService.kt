package com.gabriel.lunala.project.service

import arrow.fx.IO
import com.gabriel.lunala.project.entity.Galaxy
import com.gabriel.lunala.project.entity.LunalaPlanet
import com.gabriel.lunala.project.entity.Planet
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class DefaultPlanetService: PlanetService {

    override fun create(): IO<Planet> {
        TODO("Not yet implemented")
    }

    override fun findById(name: String): IO<Planet> = IO {
        newSuspendedTransaction {
            LunalaPlanet.findById(id) ?: error("Could not find planet with id $id.")
        }
    }

}