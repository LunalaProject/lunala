package com.gabriel.lunala.project.database

import com.gabriel.lunala.project.Lunala
import com.gabriel.lunala.project.utils.luna.createTransaction
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction

class LunalaKordDatabase(
        lunala: Lunala,
        private val tables: List<Table>
): LunalaDatabase(lunala) {

    lateinit var exposed: Database

    override fun connect() {
        val config = HikariConfig().apply {
            jdbcUrl = "jdbc:mysql://localhost:3306/lunalaDatabase?useTimezone=true&serverTimezone=UTC"
            driverClassName = "com.mysql.cj.jdbc.Driver"
            username = "root"
            password = ""
            maximumPoolSize = 8
        }

        exposed = Database.connect(HikariDataSource(config))
    }

    override suspend fun createTables() = lunala.createTransaction {
        SchemaUtils.createMissingTablesAndColumns(*tables.toTypedArray())
    }
}