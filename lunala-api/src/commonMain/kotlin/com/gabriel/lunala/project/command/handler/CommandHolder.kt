package com.gabriel.lunala.project.command.handler

import com.gabriel.lunala.project.command.Command
import com.gabriel.lunala.project.command.snapshot.SnapshotCommand

interface CommandHolder {

    val commands: MutableMap<List<String>, Command>

    fun register(command: SnapshotCommand)

}