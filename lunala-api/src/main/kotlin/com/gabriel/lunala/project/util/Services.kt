package com.gabriel.lunala.project.util

import arrow.fx.IO
import com.gabriel.lunala.project.command.CommandDSL
import com.gabriel.lunala.project.entity.Galaxy
import com.gabriel.lunala.project.entity.Planet
import com.gabriel.lunala.project.entity.Profile
import com.gabriel.lunala.project.entity.Server
import kotlinx.coroutines.Job

typealias ProfileService = Service<Long, Profile>
typealias ServerService = Service<Long, Server>
typealias GalaxyService = Service<String, Galaxy>

typealias Command = SetupAware<CommandDSL<*>>
typealias Listener = SetupAware<Job>

interface Service<ID, A> {

    fun findById(id: ID): IO<A>

    fun findOrCreateById(id: ID): IO<A>

}

interface PlanetService: Service<String, Planet> {

    fun createProcedurally(galaxy: Galaxy): IO<Planet>

    override fun findOrCreateById(id: String): IO<Planet> =
            throw UnsupportedOperationException()

}

interface SetupAware<A> {

    fun create(): IO<A>

}