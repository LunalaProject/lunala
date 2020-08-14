package com.gabriel.lunala.project.table

import com.gabriel.lunala.project.entity.LunalaProfile
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.transactions.transaction

object LunalaProfiles: LongIdTable() {

    fun findOrCreate(id: Long): LunalaProfile = transaction {
        LunalaProfile.findById(id) ?: LunalaProfile.new(id) {

        }
    }

}