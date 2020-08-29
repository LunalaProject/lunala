package com.gabriel.lunala.project.planet

import com.gabriel.lunala.project.entity.extra.Planet
import com.gabriel.lunala.project.manager.PlanetManager
import java.awt.Color

sealed class StandalonePlanet(
        name: String,
        default: Boolean = false,
        image: String,
        mass: Long,
        color: String,
        radius: Long,
        temperature: Int
): Planet(name, default, image, mass, color, radius, temperature) {

    object Relicta: StandalonePlanet(
            name = "Relicta",
            mass = 82,
            color = "#FF8207EE",
            image = "https://images.vexels.com/media/users/3/143555/isolated/preview/af8dbc9112fe8ee9328539534b5a6548-red-and-yellow-3d-question-mark-by-vexels.png",
            radius = 7281,
            temperature = -26
    )


    object Anistar: StandalonePlanet(
            name = "Anistar",
            mass = 82,
            color = "#FF8207EE",
            image = "https://images.vexels.com/media/users/3/143555/isolated/preview/af8dbc9112fe8ee9328539534b5a6548-red-and-yellow-3d-question-mark-by-vexels.png",
            radius = 7281,
            temperature = -26
    )

    object Aequor: StandalonePlanet(
            name = "Aequor",
            mass = 82L,
            color = "#FF003EFF",
            image = "https://images.vexels.com/media/users/3/143555/isolated/preview/af8dbc9112fe8ee9328539534b5a6548-red-and-yellow-3d-question-mark-by-vexels.png",
            radius = 17281,
            temperature = -52
    )

    companion object: PlanetManager {

        override val planets: List<Planet> =
                listOf(Anistar)

    }

}