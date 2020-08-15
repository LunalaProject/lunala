package com.gabriel.lunala.project.command.impl.utils

import com.gabriel.lunala.project.command.Command
import com.gabriel.lunala.project.command.api.command
import com.gabriel.lunala.project.command.handler.DiscordCommandContext
import com.gabriel.lunala.project.command.snapshot.SnapshotCommand
import com.gabriel.lunala.project.manager.PlanetManager
import com.gabriel.lunala.project.planet.MutablePlanet
import com.gabriel.lunala.project.planet.Planets
import com.gabriel.lunala.project.utils.embed
import com.gabriel.lunala.project.utils.message.LunalaReply
import com.gabriel.lunala.project.utils.stripAccents
import com.gabriel.lunala.project.utils.text.Color
import com.gitlab.kordlib.core.behavior.channel.createMessage
import org.koin.core.get
import java.text.Normalizer
import java.time.Instant

class PlanetCommand: SnapshotCommand {

    override fun create(): Command = command("planet") {
        description {
            "See info about an specific planet from the Solar System."
        }

        examples {
            listOf("Earth", "Mars")
        }

        shard<DiscordCommandContext> {
            val planet = get<PlanetManager>().planets.firstOrNull {
                it.name.toLowerCase().stripAccents() == args.getOrNull(0)?.toLowerCase()?.stripAccents() ?: false
            } as MutablePlanet?

            if (planet == null) {
                reply(LunalaReply(
                        ":no_entry_sign:",
                        ", the required planet couldn't be found in my system.",
                        mentionable = profile
                ))
                return@shard
            }

            val embed = embed {
                title = planet.name
                description = "Now showing info about the planet **${planet.name}**:"

                thumbnail {
                    url = planet.image
                }

                color = planet.color

                field {
                    name = "\uD83D\uDC8C Name"
                    value = planet.name
                    inline = true
                }
                field {
                    name = "\uD83D\uDCE6 Mass"
                    value = planet.mass.toString() + " moons"
                    inline = true
                }
                field {
                    name = "\uD83D\uDCCA Radius"
                    value = planet.radius.toString() + " km"
                    inline = true
                }
                field {
                    name = "\uD83C\uDF21 Temperature"
                    value = planet.temperature.toString() + " °C"
                    inline = true
                }
                field {
                    name = "\uD83C\uDFC1 Are you in"
                    value = if (profile.currentPlanet == planet) "Yes" else "No"
                    inline = true
                }

                footer {
                    text = "Command required by ${member.tag}"
                    icon = member.avatar.url
                }

                timestamp = Instant.now()
            }

            channel.createMessage {
                this@createMessage.embed = embed
            }
        }
    }

}