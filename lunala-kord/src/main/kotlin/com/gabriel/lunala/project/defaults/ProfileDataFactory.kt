package com.gabriel.lunala.project.defaults

import com.gabriel.lunala.project.entity.LunalaProfile
import com.gabriel.lunala.project.entity.Profile
import com.gabriel.lunala.project.planet.StandalonePlanet

object ProfileDataFactory {

    fun apply(profile: Profile): Unit = profile.run {
        require(profile is LunalaProfile)

        profile.money = 0
        profile.planet = StandalonePlanet.planets.first { it.default }
    }

}