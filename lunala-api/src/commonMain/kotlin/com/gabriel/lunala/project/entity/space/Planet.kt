package com.gabriel.lunala.project.entity.space

interface Planet {

    val name: String
    val default: Boolean
    val image: String
    val mass: Long
    val radius: Long
    val density: Float
    val temperature: Int

}