package com.gabriel.lunala.project.command

import com.gabriel.lunala.project.locale.LocaleWrapper
import me.eugeniomarletti.kotlin.metadata.shadow.utils.addToStdlib.cast

typealias CommandSet = MutableList<CommandDSL<*>>
typealias CommandTrigger = suspend CommandContext.() -> Unit
typealias CommandDescription = DescriptionDSL.(LocaleWrapper) -> Unit

data class CommandDSL<A : CommandContext>(
    val name: String,
    val aliases: List<String>,
    val category: CommandCategory,
) {

    var trigger: CommandTrigger? = null
    val subcommands: CommandSet = mutableListOf()

    var description: CommandDescription? = null

    fun description(block: CommandDescription) {
        description = block
    }

    inline fun command(name: String, vararg aliases: String, block: CommandDSL<A>.() -> Unit) {
        subcommands.add(CommandDSL<A>(name, aliases.toList(), category).apply(block))
    }

    fun trigger(block: suspend A.() -> Unit) {
        trigger = block.cast()
    }

}

class DescriptionDSL(val entries: MutableList<String> = mutableListOf()) {

    operator fun String.unaryPlus() = entries.add(this)

}

enum class CommandCategory {

    MISC,
    UTILS;

}