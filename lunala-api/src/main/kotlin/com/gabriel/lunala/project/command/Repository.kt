package com.gabriel.lunala.project.command

class CommandRepository(val commands: MutableMap<String, CommandDSL<*>> = mutableMapOf()) {

    fun register(command: CommandDSL<*>) {
        commands[command.name] = command
    }

    fun retrieve(name: String): CommandDSL<*>? =
        commands[name]

    fun retrieveWithAliases(name: String): CommandDSL<*>? =
        commands.values.firstOrNull {
            it.aliases.plus(it.name).contains(name)
        }

    fun exclude(command: CommandDSL<*>) {
        commands.remove(command.name, command)
    }

}