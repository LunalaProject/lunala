package com.gabriel.lunala.project.util

import arrow.fx.IO
import com.gabriel.lunala.project.entity.Planet
import com.gabriel.lunala.project.entity.Profile
import com.gabriel.lunala.project.entity.Server

typealias ProfileService = Service<Long, Profile>
typealias ServerService = Service<Long, Server>
typealias PlanetService = Service<String, Planet>

interface Service<ID, A> {

    fun findById(id: ID): IO<A>

    fun findOrCreateById(id: ID): IO<A>

}