package com.gabriel.lunala.project.entity

interface Planet {

    val name: String
    var distance: Long
    var budget: Long
    var security: Float
    var owner: Long
    var visited: Boolean

}