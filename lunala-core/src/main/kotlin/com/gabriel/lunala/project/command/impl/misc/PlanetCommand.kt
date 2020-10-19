package com.gabriel.lunala.project.command.impl.misc

import com.gabriel.lunala.project.command.CommandCategory
import com.gabriel.lunala.project.util.PlanetService
import com.gabriel.lunala.project.util.ProfileService
import com.gabriel.lunala.project.util.command
import com.gabriel.lunala.project.util.embed
import java.awt.Color
import java.time.Instant

class PlanetCommand(val service: PlanetService, val profileService: ProfileService) {

    fun create() = command("planet", "planetinfo", category = CommandCategory.MISCELLANEOUS) {
        description {
            + it["commands.miscellaneous.planetinfo.description"]
        }

        trigger {
            val planet = runCatching {
                service.findById(args.joinToString(" ")).suspended()
            }.onFailure {
                reply {
                    append {
                        prefix = "❌"
                        message = locale["commands.miscellaneous.planetinfo.notFound"]
                    }
                }
            }.getOrNull() ?: return@trigger

            reply {
                append {
                    embed = embed {
                        title = "✨ | Info"
                        description = locale["commands.miscellaneous.planetinfo.information", planet.name]

                        color = Color(87, 6, 153)

                        field {
                            name = "\uD83D\uDCCC " + locale["utils.name"]
                            value = planet.name
                            inline = true
                        }
                        field {
                            name = "\uD83D\uDCCA " + locale["utils.distance"]
                            value = planet.distance.toString() + "km"
                            inline = true
                        }
                        field {
                            name = "\uD83D\uDCB8 " + locale["utils.budget"]
                            value = (planet.distance / 1000).toString() + " lunas"
                            inline = true
                        }
                        field {
                            name = "\uD83D\uDC6E " + locale["utils.security"]
                            value = planet.security.toString() + "%"
                            inline = true
                        }
                        field {
                            name = "\uD83C\uDF0D " + locale["utils.colonizer"]
                            value = planet.owner?.toString() ?: "Nenhum"
                            inline = true
                        }
                        field {
                            name = "\uD83C\uDF0C" + locale["utils.galaxy"]
                            value = planet.galaxy
                            inline = true
                        }

                        thumbnail {
                            url = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/97/The_Earth_seen_from_Apollo_17.jpg/1200px-The_Earth_seen_from_Apollo_17.jpg"
                        }

                        footer {
                            icon = user.avatar.url
                            text = "Call from ${user.tag}"
                        }

                        timestamp = Instant.now()
                    }
                }
            }
        }
    }

}