package com.gabriel.lunala.project.route

import com.gabriel.lunala.project.entity.Profile
import com.gabriel.lunala.project.entity.extra.Planet
import com.gabriel.lunala.project.lifecycle.ILifecycle
import com.gabriel.lunala.project.sql.LunalaProfile
import net.dv8tion.jda.api.entities.TextChannel

interface BattleRoute {

    suspend fun init(profile: Profile, channel: TextChannel)

}