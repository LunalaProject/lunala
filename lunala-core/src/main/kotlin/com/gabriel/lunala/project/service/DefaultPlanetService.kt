package com.gabriel.lunala.project.service

import arrow.fx.IO
import com.gabriel.lunala.project.entity.Galaxy
import com.gabriel.lunala.project.entity.LunalaPlanet
import com.gabriel.lunala.project.entity.Planet
import com.gabriel.lunala.project.util.PlanetService
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class DefaultPlanetService: PlanetService {

    override fun createProcedurally(galaxy: Galaxy): IO<Planet> {
        TODO("Not yet implemented")
    }

    override fun findById(id: String): IO<Planet> = IO {
        newSuspendedTransaction {
            LunalaPlanet.findById(id) ?: error("Could not find planet with id $id.")
        }
    }

}