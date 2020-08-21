package com.gabriel.lunala.project.entity.extra

import com.gabriel.lunala.project.entity.Profile
import com.gabriel.lunala.project.utils.Rarity
import kotlin.jvm.JvmOverloads

abstract class Ore @JvmOverloads constructor(
        val name: String,
        val planets: List<Planet>,
        val rarity: Rarity,
        val block: (Profile) -> Unit = {}
)