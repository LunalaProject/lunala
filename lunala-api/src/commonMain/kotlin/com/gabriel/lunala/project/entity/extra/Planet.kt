package com.gabriel.lunala.project.entity.extra

abstract class Planet(
        val name: String,
        val default: Boolean = false,
        val image: String,
        val mass: Long,
        val color: String,
        val radius: Long,
        val temperature: Int
)