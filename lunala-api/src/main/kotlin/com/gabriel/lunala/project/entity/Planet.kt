package com.gabriel.lunala.project.entity

import com.gabriel.lunala.project.util.GalaxyService

interface Planet {

    val name: String
    var distance: Long
    var security: Float
    var owner: Long?
    var galaxy: String
    var visited: Boolean

    suspend fun getGalaxy(service: GalaxyService): Galaxy

}