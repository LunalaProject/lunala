package com.gabriel.lunala.project.entity

import com.gabriel.lunala.project.utils.Mentionable
import com.gabriel.lunala.project.utils.flaging.Priority

interface Profile: Mentionable {

    override val asMention: String
        get() = "<@$idLong>"

    val idLong: Long
    val money: Long
    val priority: Priority

}