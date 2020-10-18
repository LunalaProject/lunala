package com.gabriel.lunala.project.entity

import com.gabriel.lunala.project.util.PlanetService

interface Galaxy {

    val name: String
    val defaultPlanet: String

    suspend fun getDefaultPlanet(service: PlanetService): Planet

}