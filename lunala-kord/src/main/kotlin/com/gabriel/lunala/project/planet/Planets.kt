package com.gabriel.lunala.project.planet

import com.gabriel.lunala.project.entity.space.Planet
import com.gabriel.lunala.project.manager.PlanetManager
import java.awt.Color

object Planets: PlanetManager {

    val SUN = planet("Sun") {
        mass = 27066268
        color = Color(255, 0, 0)
        image = "https://nineplanets.org/wp-content/uploads/2019/09/sun.png"
        radius = 702098
        density = 1.37F
        temperature = 5505
    }

    val MERCURY = planet("Mercury") {
        mass = 50
        color = Color(200, 134, 20)
        image = "https://vignette.wikia.nocookie.net/thesolarsystem6361/images/5/50/Mercury_spacepedia.png/revision/latest/scale-to-width-down/340?cb=20180301162112"
        radius = 2440
        density = 5.43F
        temperature = 167
    }

    val VENUS = planet("Venus") {
        mass = 66
        color = Color(227, 202,168)
        image = "https://meioambiente.culturamix.com/blog/wp-content/uploads/2010/06/46.gif"
        radius = 6052
        density = 5.24F
        temperature = 471
    }

    val EARTH = planet("Earth",true) {
        mass = 81
        color = Color(0, 255 ,123)
        image = "https://images-na.ssl-images-amazon.com/images/I/91NLJkVVRFL.png"
        radius = 6371
        density = 5.51F
        temperature = 16
    }

    val MARS = planet("Mars") {
        mass = 9
        color = Color(255, 162, 25)
        image = "https://vignette.wikia.nocookie.net/thesolarsystem6361/images/0/0e/Mars_spacepedia.png/revision/latest/scale-to-width-down/340?cb=20180301164546"
        radius = 3390
        density = 3.93F
        temperature = -28
    }

    val JUPITER = planet("Jupiter") {
        mass = 318
        color = Color(239, 180, 32)
        image = "https://images.squarespace-cdn.com/content/5881082829687f4fd5a8b95c/1561601104491-E65Z5RIG6HGBTPE0DFXR/jupiter+blank+background.png?content-type=image%2Fpng"
        radius = 69911
        density = 1.33F
        temperature = -108
    }

    val SATURN = planet("Saturn") {
        mass = 95
        color = Color(152, 30, 50)
        image = "https://nineplanets.org/wp-content/uploads/2019/09/saturn.png"
        radius = 58232
        density = 0.687F
        temperature = -138
    }

    val URANUS = planet("Uranus") {
        mass = 1181
        color = Color(150, 232, 252)
        image = "https://www.solarsystemscope.com/spacepedia/images/handbook/renders/uranus.png"
        radius = 25362
        density =  1.27F
        temperature = -195
    }

    val NEPTUNE = planet("Neptune") {
        mass = 1394
        color = Color(0, 165,255)
        image = "https://nineplanets.org/wp-content/uploads/2019/09/neptune.png"
        radius = 24622
        density = 1.64F
        temperature = -201
    }

    override val planets: MutableList<Planet> = mutableListOf(SUN, MERCURY, VENUS, EARTH, MARS, JUPITER, SATURN, URANUS, NEPTUNE)

}

class MutablePlanet(override val name: String, override val default: Boolean): Planet {

    override var mass: Long = 1
    override var image: String = "None"
    override var radius: Long = 3000
    override var density: Float = 1.0F
    override var temperature: Int = 50

    var color: Color = Color.BLACK

}

private fun planet(name: String, default: Boolean = false, base: MutablePlanet = MutablePlanet(name, default), block: MutablePlanet.() -> Unit) =
        base.apply(block)

