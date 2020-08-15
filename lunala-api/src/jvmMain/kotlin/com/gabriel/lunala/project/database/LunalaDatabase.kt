package com.gabriel.lunala.project.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction

class LunalaDatabase(private val tables: List<Table>) {

    lateinit var exposed: Database

    fun connect() {
        val config = HikariConfig().apply {
            jdbcUrl = "jdbc:mysql://localhost:3306/lunalaDatabase?useTimezone=true&serverTimezone=UTC"
            driverClassName = "com.mysql.cj.jdbc.Driver"
            username = "root"
            password = ""
            maximumPoolSize = 8
        }

        exposed = Database.connect(HikariDataSource(config))
    }

    fun createTables() = transaction {
        SchemaUtils.drop(*tables.toTypedArray())
        SchemaUtils.createMissingTablesAndColumns(*tables.toTypedArray())
    }

}