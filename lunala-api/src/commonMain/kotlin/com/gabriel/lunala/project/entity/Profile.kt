package com.gabriel.lunala.project.entity

import com.gabriel.lunala.project.utils.Mentionable
import com.gabriel.lunala.project.utils.flaging.Priority

interface Profile: Mentionable {

    override val asMention: String
        get() = "<@$idLong>"

    val idLong: Long

    var level: Int
    var money: Long
    var priority: Priority
    var tripulation: Int
    var travelingTime: Long?

}