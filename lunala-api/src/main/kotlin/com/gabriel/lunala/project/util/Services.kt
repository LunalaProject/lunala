package com.gabriel.lunala.project.util

import arrow.fx.IO
import com.gabriel.lunala.project.entity.Profile
import com.gabriel.lunala.project.entity.Server

typealias ProfileService = SnowflakeService<Profile>
typealias ServerService = SnowflakeService<Server>

interface SnowflakeService<A> {

    fun findById(id: Long): IO<A>

    fun findOrCreateById(id: Long): IO<A>

}