package com.gabriel.lunala.project.entity.extra

import com.gabriel.lunala.project.utils.Rarity

interface Achievement {

    val name: String
    val description: String
    val rarity: Rarity

}