package com.gabriel.lunala.project.util

import com.gabriel.lunala.project.entity.Guild
import com.gabriel.lunala.project.entity.GuildCreateDTO
import com.gabriel.lunala.project.entity.User
import com.gabriel.lunala.project.entity.UserCreateDTO

typealias LunalaProfile = User
typealias LunalaGuild = Guild

val LunalaProfile.mention: String
    get() = "<@$id>"

fun User.Companion.default(id: Long) = UserCreateDTO(
    id,
    1,
    500,
    1,
    5,
    "Earth",
    "Milky Way",
    PremiumType.NONE
)

fun Guild.Companion.default(id: Long) = GuildCreateDTO(
    id,
    "en-us",
    false
)