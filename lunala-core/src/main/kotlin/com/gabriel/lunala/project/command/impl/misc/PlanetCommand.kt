package com.gabriel.lunala.project.command.impl.misc

import com.gabriel.lunala.project.command.CommandCategory
import com.gabriel.lunala.project.util.PlanetService
import com.gabriel.lunala.project.util.ProfileService
import com.gabriel.lunala.project.util.command
import com.gabriel.lunala.project.util.embed

class PlanetCommand(val service: PlanetService, val profileService: ProfileService) {

    fun create() = command("planet", "planetinfo", category = CommandCategory.MISCELLANEOUS) {
        description {
            + it["commands.miscellaneous.planetinfo.description"]
        }

        trigger {
            val planet = service.findById(args.joinToString(" ")).suspended()

            reply {
                append {
                    prefix = "❌"
                    message = locale["commands.miscellaneous.planetinfo.notFound"]
                }
            }

            reply {
                embed {
                    title = "✨ | Info"
                    description = locale["commands.miscellaneous.planetinfo.information", planet.name]

                    field {
                        name = "\uD83D\uDCCC " + locale["utils.name"]
                        value = planet.name
                        inline = true
                    }
                    field {
                        name = "\uD83D\uDCCA " + locale["utils.distance"]
                        value = planet.distance.toString()
                        inline = true
                    }
                    field {
                        name = "\uD83D\uDCB8 " + locale["utils.budget"]
                        value = planet.budget.toString()
                        inline = true
                    }
                    field {
                        name = "\uD83D\uDC6E " + locale["utils.security"]
                        value = planet.security.toString()
                        inline = true
                    }
                    field {
                        name = "\uD83C\uDF0D " + locale["utils.colonizer"]
                        value = planet.owner.toString()
                        inline = true
                    }
                    field {
                        name = "\uD83D\uDC7D " + locale["utils.visited"]
                        value = planet.visited.toString()
                        inline = true
                    }
                }
            }
        }
    }

}