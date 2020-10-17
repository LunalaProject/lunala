package com.gabriel.lunala.project.entity

import com.gabriel.lunala.project.util.MentionAware
import com.gabriel.lunala.project.util.Service

interface Profile: MentionAware {

    val idLong: Long

    var ship: Int
    var coins: Long
    var equipment: Int
    var crew: Int
    var planet: String
    var galaxy: String

    suspend fun getPlanet(service: Service<String, Planet>): Planet

    override fun mention(): String = "<@$idLong>"

}