package com.gabriel.lunala.project.table

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.timestamp

object LunalaAchievements: Table() {

    val userId = long("user_id")
    val achievement = varchar("achievement", 32)
    val date = timestamp("date")

}