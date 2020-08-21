package com.gabriel.lunala.project.table

import com.gabriel.lunala.project.entity.LunalaProfile
import com.gabriel.lunala.project.manager.PlanetManager
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.KoinComponent
import org.koin.core.get

object LunalaProfiles: LongIdTable(), KoinComponent {

    val money = long("money")
    val planet = varchar("current_planet", 18)

}