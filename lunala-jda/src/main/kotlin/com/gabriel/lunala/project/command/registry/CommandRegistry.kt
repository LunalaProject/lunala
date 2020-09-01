package com.gabriel.lunala.project.command.registry

import com.gabriel.lunala.project.command.handler.CommandHolder
import com.gabriel.lunala.project.command.impl.status.StatusCommand

class CommandRegistry {

    fun register(holder: CommandHolder): Unit = holder.let {
        it.register(StatusCommand())
    }

}