package com.gabriel.lunala.project.command

import com.gabriel.lunala.project.util.kotlin.cast

typealias CommandSet = MutableList<CommandDSL<*>>
typealias CommandTrigger = suspend CommandContext.() -> Unit

data class CommandDSL<A : CommandContext>(
    val name: String,
    val aliases: List<String>,
    val category: CommandCategory,
) {

    var trigger: CommandTrigger? = null
    val subcommands: CommandSet = mutableListOf()

    inline fun command(name: String, vararg aliases: String, block: CommandDSL<A>.() -> Unit) {
        subcommands.add(CommandDSL<A>(name, aliases.toList(), category).apply(block))
    }

    fun executor(block: suspend A.() -> Unit) {
        trigger = block.cast()
    }

}

enum class CommandCategory {

    ACTIONS,
    MISCELLANEOUS,
    UTILS;

}