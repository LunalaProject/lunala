package com.gabriel.lunala.project.route

import com.gabriel.lunala.project.entity.Profile
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.TextChannel

interface BattleHandler {

    suspend fun init(profile: Profile, member: Member, channel: TextChannel)

}