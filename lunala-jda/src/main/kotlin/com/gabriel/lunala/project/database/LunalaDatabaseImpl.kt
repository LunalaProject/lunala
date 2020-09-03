package com.gabriel.lunala.project.database

import com.gabriel.lunala.project.Lunala
import com.gabriel.lunala.project.utils.client.standardTransaction
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table

class LunalaDatabaseImpl(
        lunala: Lunala,
        private val tables: List<Table>
): LunalaDatabase(lunala) {

    lateinit var database: Database

    override suspend fun connect() {
        val config = HikariConfig().apply {
            jdbcUrl = "jdbc:mysql://localhost:3306/lunalaDatabase?useTimezone=true&serverTimezone=UTC"
            driverClassName = "com.mysql.cj.jdbc.Driver"
            username = "root"
            password = ""
            maximumPoolSize = 8
        }

        database = Database.connect(HikariDataSource(config))
    }

    override suspend fun createTables() = lunala.standardTransaction {
        SchemaUtils.drop(*tables.toTypedArray())
        SchemaUtils.createMissingTablesAndColumns(*tables.toTypedArray())
    }
}