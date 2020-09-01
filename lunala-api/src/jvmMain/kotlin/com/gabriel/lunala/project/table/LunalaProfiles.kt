package com.gabriel.lunala.project.table

import org.jetbrains.exposed.dao.id.LongIdTable
import org.koin.core.KoinComponent

object LunalaProfiles: LongIdTable(), KoinComponent {

    val money = long("money")

}