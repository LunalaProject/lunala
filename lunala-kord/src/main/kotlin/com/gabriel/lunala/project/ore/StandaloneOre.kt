package com.gabriel.lunala.project.ore

import com.gabriel.lunala.project.entity.extra.Ore
import com.gabriel.lunala.project.entity.extra.Planet
import com.gabriel.lunala.project.planet.StandalonePlanet
import com.gabriel.lunala.project.utils.Rarity

sealed class StandaloneOre(
        name: String,
        planets: List<Planet>,
        rarity: Rarity
): Ore(name, planets, rarity) {

    object Anellite: StandaloneOre(
            name = "Anellite",
            planets = listOf(StandalonePlanet.Anistar, StandalonePlanet.Relicta),
            rarity = Rarity.COMMON
    )

    object Brannite: StandaloneOre(
            name = "Brannite",
            planets = listOf(),
            rarity = Rarity.RARE
    )

    object Cinnamite: StandaloneOre(
            name = "Cinnamite",
            planets = listOf(),
            rarity = Rarity.UNCOMMON
    )

    object Eanerite: StandaloneOre(
            name = "Earenite",
            planets = listOf(StandalonePlanet.Relicta),
            rarity = Rarity.COMMON
    )

    object Pupnite: StandaloneOre(
            name = "Pupnite",
            planets = listOf(),
            rarity = Rarity.EPIC
    )

    object Kirinite: StandaloneOre(
            name = "Kirinite",
            planets = listOf(),
            rarity = Rarity.COMMON
    )

    object Rignite: StandaloneOre(
            name = "Rignite",
            planets = listOf(StandalonePlanet.Anistar),
            rarity = Rarity.EPIC
    )

    companion object {

        val values = listOf(Anellite, Brannite, Cinnamite, Eanerite, Pupnite, Kirinite, Rignite)

    }

}