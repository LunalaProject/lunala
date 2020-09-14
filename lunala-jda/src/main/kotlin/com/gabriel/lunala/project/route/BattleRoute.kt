package com.gabriel.lunala.project.route

import com.gabriel.lunala.project.entity.Profile
import net.dv8tion.jda.api.entities.TextChannel

interface BattleRoute {

    suspend fun init(profile: Profile, channel: TextChannel)

}