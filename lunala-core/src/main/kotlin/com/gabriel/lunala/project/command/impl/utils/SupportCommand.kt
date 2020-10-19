package com.gabriel.lunala.project.command.impl.utils

import com.gabriel.lunala.project.command.CommandCategory
import com.gabriel.lunala.project.util.command

class SupportCommand {

    fun create() = command("support", category = CommandCategory.UTILS) {
        description {
            +it["commands.utils.support.description"]
        }

        trigger {
            reply {
                append {
                    prefix = "\uD83D\uDCDC"
                    message = locale["commands.utils.support.server-info"]
                }
                append {
                    prefix = "\uD83C\uDF03"
                    message = locale["commands.utils.support.server-invite", cluster.config.discord.discord]
                }
            }
        }
    }
}