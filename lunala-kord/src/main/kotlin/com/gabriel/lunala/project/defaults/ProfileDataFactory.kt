package com.gabriel.lunala.project.defaults

import com.gabriel.lunala.project.entity.LunalaProfile
import com.gabriel.lunala.project.entity.Profile
import com.gabriel.lunala.project.planet.StandalonePlanets

object ProfileDataFactory {

    fun apply(profile: Profile): Unit = profile.run {
        require(profile is LunalaProfile)

        profile.money = 0
        profile.planet = StandalonePlanets.planets.first { it.default }
    }

}