package com.gabriel.lunala.project.service

import arrow.fx.IO
import com.gabriel.lunala.project.entity.Planet

interface PlanetService {

    fun create(): IO<Planet>

    fun findById(name: String): IO<Planet>

}