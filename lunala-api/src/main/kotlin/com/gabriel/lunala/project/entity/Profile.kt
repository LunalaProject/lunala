package com.gabriel.lunala.project.entity

import com.gabriel.lunala.project.util.MentionAware

interface Profile: MentionAware {

    val idLong: Long
    var coins: Long

    override fun mention(): String = "<@$idLong>"

}