package com.gabriel.lunala.project.entity

import com.gabriel.lunala.project.entity.space.Planet
import com.gabriel.lunala.project.utils.Mentionable

interface Profile: Mentionable {

    override val asMention: String
        get() = "<@$idLong>"

    val idLong: Long
    val currentPlanet: Planet

}