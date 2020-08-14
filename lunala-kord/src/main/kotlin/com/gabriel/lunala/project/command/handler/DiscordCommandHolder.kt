package com.gabriel.lunala.project.command.handler

import com.gabriel.lunala.project.command.Command
import com.gabriel.lunala.project.command.snapshot.SnapshotCommand

class DiscordCommandHolder: CommandHolder {

    override val commands: MutableMap<List<String>, Command> = mutableMapOf()

    override fun register(command: SnapshotCommand) {
        val built = command.create()

        commands[built.labels] = built
    }

}