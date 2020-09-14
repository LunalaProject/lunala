package com.gabriel.lunala.project.command.impl.staff

import com.gabriel.lunala.project.command.Command
import com.gabriel.lunala.project.command.handler.DiscordCommandContext
import com.gabriel.lunala.project.command.snapshot.SnapshotCommand
import com.gabriel.lunala.project.command.utils.command
import com.gabriel.lunala.project.emojis.Emote
import com.gabriel.lunala.project.module.LunalaModuleController
import com.gabriel.lunala.project.module.StandardModuleController
import com.gabriel.lunala.project.utils.flaging.Priority
import com.gabriel.lunala.project.utils.message.LunaReply
import org.koin.core.get

class ModulesCommand: SnapshotCommand {

    val controller: StandardModuleController = get<LunalaModuleController>() as StandardModuleController

    override fun create(): Command = command("modules", "module") {
        shard<DiscordCommandContext>(priority = Priority.MODERATED) {
            val active = controller.modules.filter {
                it.value.enabled
            }.values

            if (active.isEmpty()) {
                reply(LunaReply(
                        ":no_entry_sign:",
                        "No active modules running!",
                        profile
                ))
                return@shard
            }

            reply(LunaReply(
                    ":sparkles:",
                    "We have **${active.size}** modules running right now!",
                    profile
            ), LunaReply(
                    ":large_blue_diamond:",
                    "**Active modules:** ${active.joinToString(", ") { it.name }}"
            ))
        }

        shard<DiscordCommandContext>("enable", priority = Priority.SEVERE) {
            val module = controller.parse(args.getOrNull(0)) ?: return@shard

            if (module.enabled) {
                reply(LunaReply(
                        "${Emote.Warn}",
                        "The module **${module.name}** is already enabled!",
                        profile
                ))
                return@shard
            }

            controller.load(module)

            reply(LunaReply(
                    "${Emote.Check}",
                    "The module **${module.name}** was enabled successfully!",
                    profile
            ))
        }

        shard<DiscordCommandContext>("reload", priority = Priority.MODERATED) {
            val module = controller.parse(args.getOrNull(0)) ?: return@shard

            controller.unload(module)
            controller.load(module)

            reply(LunaReply(
                    "${Emote.Check}",
                    "The module **${module.name}** was reloaded successfully!",
                    profile
            ))
        }

        shard<DiscordCommandContext>("disable", priority = Priority.SEVERE) {
            val module = controller.modules[args.getOrNull(0)] ?: return@shard

            if (!module.enabled) {
                reply(LunaReply(
                        "${Emote.Warn}",
                        "The module **${module.name}** is already disabled!",
                        profile
                ))
                return@shard
            }

            controller.unload(module)

            reply(LunaReply(
                    "${Emote.Check}",
                    "The module **${module.name}** was disabled successfully!",
                    profile
            ))
        }
    }

}