package com.gabriel.lunala.project.entity

import com.gabriel.lunala.project.premium.Premium
import com.gabriel.lunala.project.util.MentionAware
import com.gabriel.lunala.project.util.PlanetService
import com.gabriel.lunala.project.util.Service

interface Profile: MentionAware {

    val idLong: Long

    var ship: Int
    var coins: Long
    var equipment: Int
    var crew: Int
    var planet: String
    var galaxy: String
    var premium: Premium

    suspend fun getPlanet(service: PlanetService): Planet

    override fun mention(): String = "<@$idLong>"

}