package com.gabriel.lunala.project.command.impl.adventure

import com.gabriel.lunala.project.achievements.AchievementHandler
import com.gabriel.lunala.project.achievements.Achievements
import com.gabriel.lunala.project.command.Command
import com.gabriel.lunala.project.command.api.command
import com.gabriel.lunala.project.command.api.fail
import com.gabriel.lunala.project.command.handler.DiscordCommandContext
import com.gabriel.lunala.project.command.snapshot.SnapshotCommand
import com.gabriel.lunala.project.manager.PlanetManager
import com.gabriel.lunala.project.planet.StandalonePlanets
import com.gabriel.lunala.project.utils.message.LunaReply
import com.gabriel.lunala.project.utils.stripAccents
import org.koin.core.get

class TravelCommand: SnapshotCommand {

    override fun create(): Command = command("travel") {
        shard<DiscordCommandContext> {
            val planet = get<PlanetManager>().planets.firstOrNull {
                it.name.toLowerCase().stripAccents() == args.getOrNull(0)?.toLowerCase()?.stripAccents() ?: false
            } ?: fail {
                reply(LunaReply(":no_entry_sign", ", the required planet couldn't be found in my system.", profile))
            }

            if (planet == profile.planet)
                fail { reply(LunaReply(":no_entry_sign:", ", you're already on this planet, silly!", profile)) }

            if (planet == StandalonePlanets.Sun)
                fail { get<AchievementHandler>().send(profile, channel, Achievements.OVERHEAT) }


        }
    }

}