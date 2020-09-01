package com.gabriel.lunala.project.command.handler

import com.gabriel.lunala.project.command.Command
import com.gabriel.lunala.project.command.snapshot.SnapshotCommand

class DiscordCommandHolder: CommandHolder {

    override val commands: MutableMap<List<String>, Command> = mutableMapOf()

    override fun register(command: SnapshotCommand) = command.create().let {
        commands[it.labels] = it
    }

}