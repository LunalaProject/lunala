package com.gabriel.lunala.project.service

import arrow.Kind
import arrow.fx.ForIO
import arrow.fx.IO
import arrow.fx.extensions.fx
import com.gabriel.lunala.project.entity.LunalaGalaxies
import com.gabriel.lunala.project.entity.LunalaPlanets
import com.gabriel.lunala.project.entity.LunalaProfiles
import com.gabriel.lunala.project.entity.LunalaServers
import com.gabriel.lunala.project.util.LunalaDiscordConfig
import com.gabriel.lunala.project.util.inspect
import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.serialization.hocon.Hocon
import kotlinx.serialization.hocon.decodeFromConfig
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class DefaultDatabaseService(config: LunalaDiscordConfig): DatabaseService {
    private val section = config.database

    override fun connect(): Kind<ForIO, Unit> = IO {
        val configuration = HikariConfig().apply {
            jdbcUrl = "jdbc:mysql://${section.host}:${section.port}/${section.database}?useTimezone=true&serverTimezone=UTC"
            driverClassName = "com.mysql.cj.jdbc.Driver"
            username = section.user
            password = section.password
            maximumPoolSize  =32
            idleTimeout = 30000
            poolName = "HikariPool"
        }

        Database.connect(HikariDataSource(configuration))
    }

    override fun createTables(): Kind<ForIO, Unit> = IO {
        transaction {
            try {
                SchemaUtils.createMissingTablesAndColumns(LunalaProfiles, LunalaServers, LunalaPlanets, LunalaGalaxies)
            } catch (exception: Throwable) {
                exception.printStackTrace()
            }
        }
    }

}