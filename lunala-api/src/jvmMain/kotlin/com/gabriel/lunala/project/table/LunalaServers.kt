package com.gabriel.lunala.project.table

import com.gabriel.lunala.project.entity.LunalaProfile
import com.gabriel.lunala.project.entity.LunalaServer
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.transactions.transaction

object LunalaServers: LongIdTable() {

    fun findOrCreate(id: Long) = transaction {
        LunalaServer.findById(id) ?: LunalaServer.new(id) {

        }
    }

}