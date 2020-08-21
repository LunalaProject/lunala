package com.gabriel.lunala.project.ore

import com.gabriel.lunala.project.entity.extra.Ore
import com.gabriel.lunala.project.planet.StandalonePlanets
import com.gabriel.lunala.project.utils.Rarity

sealed class Ores{

    object Anellite: Ore(
            name = "Anellite",
            planets = listOf(StandalonePlanets.Neptune, StandalonePlanets.Mercury),
            rarity = Rarity.COMMON
    )

    object Brannite: Ore(
            name = "Brannite",
            planets = listOf(StandalonePlanets.Jupiter),
            rarity = Rarity.RARE
    )

    object Cinnamite: Ore(
            name = "Cinnamite",
            planets = listOf(StandalonePlanets.Venus),
            rarity = Rarity.UNCOMMON
    )

    object Eanerite: Ore(
            name = "Earenite",
            planets = listOf(StandalonePlanets.Earth, StandalonePlanets.Mercury, StandalonePlanets.Venus),
            rarity = Rarity.COMMON
    )

    object Pupnite: Ore(
            name = "Pupnite",
            planets = listOf(StandalonePlanets.Neptune, StandalonePlanets.Uranus, StandalonePlanets.Earth),
            rarity = Rarity.EPIC
    )

    object Kirinite: Ore(
            name = "Kirinite",
            planets = listOf(StandalonePlanets.Mars),
            rarity = Rarity.COMMON
    )

    object Rignite: Ore(
            name = "Rignite",
            planets = listOf(StandalonePlanets.Saturn),
            rarity = Rarity.EPIC
    )

}