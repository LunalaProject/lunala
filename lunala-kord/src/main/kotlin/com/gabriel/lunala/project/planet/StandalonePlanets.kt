package com.gabriel.lunala.project.planet

import com.gabriel.lunala.project.entity.extra.Planet
import com.gabriel.lunala.project.manager.PlanetManager

sealed class StandalonePlanets(
        name: String,
        default: Boolean = false,
        image: String,
        mass: Long,
        color: String,
        radius: Long,
        density: Float,
        temperature: Int
): Planet(name, default, image, mass, color, radius, density, temperature) {

    object Sun: StandalonePlanets(
            name = "Sun",
            mass = 27066268,
            color = "#ff0000",
            image = "https://nineplanets.org/wp-content/uploads/2019/09/sun.png",
            radius = 702098,
            density = 1.37F,
            temperature = 5505
    )

    object Mercury: Planet(
            name = "Mercury",
            mass = 50,
            color = "#c88614",
            image = "https://vignette.wikia.nocookie.net/thesolarsystem6361/images/5/50/Mercury_spacepedia.png/revision/latest/scale-to-width-down/340?cb=20180301162112",
            radius = 2440,
            density = 5.43F,
            temperature = 167
    )

    object Venus: Planet(
            name = "Venus",
            mass = 66,
            color = "#e3caa8",
            image = "https://meioambiente.culturamix.com/blog/wp-content/uploads/2010/06/46.gif",
            radius = 6052,
            density = 5.24F,
            temperature = 471
    )

    object Earth: Planet(
            name = "Earth",
            mass = 81,
            color = "#00ff7b",
            image = "",
            radius = 6371,
            density = 5.51F,
            temperature = 16
    )

    object Mars: Planet(
            name = "Mars",
            mass = 9,
            color = "#ffa219",
            image = "https://vignette.wikia.nocookie.net/thesolarsystem6361/images/0/0e/Mars_spacepedia.png/revision/latest/scale-to-width-down/340?cb=20180301164546",
            radius = 3390,
            density = 3.93F,
            temperature = -28
    )

    object Jupiter: Planet(
            name = "Jupiter",
            mass = 318,
            color = "#efb420",
            image = "https://images.squarespace-cdn.com/content/5881082829687f4fd5a8b95c/1561601104491-E65Z5RIG6HGBTPE0DFXR/jupiter+blank+background.png?content-type=image%2Fpng",
            radius = 69911,
            density = 1.33F,
            temperature = -108
    )

    object Saturn: Planet(
            name = "Saturn",
            mass = 95,
            color = "#efb420",
            image = "https://nineplanets.org/wp-content/uploads/2019/09/saturn.png",
            radius = 58232,
            density = 0.687F,
            temperature = -138
    )

    object Uranus: Planet(
            name = "Uranus",
            mass = 1181,
            color = "#96e8fc",
            image = "https://www.solarsystemscope.com/spacepedia/images/handbook/renders/uranus.png",
            radius = 25362,
            density = 1.27F,
            temperature = -195
    )

    object Neptune: Planet(
            name = "Neptune",
            mass = 1394,
            color = "#00a5ff",
            image = "https://nineplanets.org/wp-content/uploads/2019/09/neptune.png",
            radius = 24622,
            density = 1.64F,
            temperature = -201
    )

    companion object: PlanetManager {

        override val planets: List<Planet> =
                listOf(Sun, Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune)

    }

}