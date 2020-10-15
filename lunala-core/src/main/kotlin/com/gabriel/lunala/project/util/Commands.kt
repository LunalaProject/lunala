package com.gabriel.lunala.project.util

import com.gabriel.lunala.project.command.CommandCategory
import com.gabriel.lunala.project.command.CommandDSL
import com.gabriel.lunala.project.command.CommandRepository
import com.gabriel.lunala.project.command.DiscordCommandContext

inline fun command(
    vararg names: String,
    category: CommandCategory,
    block: CommandDSL<DiscordCommandContext>.() -> Unit
): CommandDSL<DiscordCommandContext> = CommandDSL<DiscordCommandContext>(names.first(), names.toList().minus(names.first()), category).apply(block).also {
    CommandRepository.register(it)
}
