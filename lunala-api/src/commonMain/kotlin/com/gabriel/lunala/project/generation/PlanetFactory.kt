package com.gabriel.lunala.project.generation

import com.gabriel.lunala.project.entity.Profile
import com.gabriel.lunala.project.entity.extra.Planet

interface PlanetFactory {

    fun create(profile: Profile, current: Planet): Planet

}