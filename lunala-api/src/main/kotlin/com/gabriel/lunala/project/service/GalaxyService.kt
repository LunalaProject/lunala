package com.gabriel.lunala.project.service

import arrow.fx.IO
import com.gabriel.lunala.project.entity.Galaxy

interface GalaxyService {

    fun findById(name: String): IO<Galaxy>

}