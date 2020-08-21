package com.gabriel.lunala.project.utils.luna

import com.gabriel.lunala.project.Lunala
import com.gabriel.lunala.project.defaults.ProfileDataFactory
import com.gabriel.lunala.project.entity.LunalaProfile
import com.gabriel.lunala.project.entity.Profile
import org.jetbrains.exposed.sql.transactions.transaction

fun Lunala.getProfile(snowflake: Long): Profile? = transaction {
    LunalaProfile.findById(snowflake)
}

fun Lunala.getProfileOrCreate(snowflake: Long) = transaction {
    LunalaProfile.findById(snowflake) ?: LunalaProfile.new(snowflake) {
        ProfileDataFactory.apply(this)
    }
}