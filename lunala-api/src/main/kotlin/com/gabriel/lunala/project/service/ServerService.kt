package com.gabriel.lunala.project.service

import arrow.fx.IO
import com.gabriel.lunala.project.entity.Server

interface ServerService {

    fun findById(id: Long): IO<Server>

    fun findOrCreateById(id: Long): IO<Server>

}