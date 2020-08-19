package com.gabriel.lunala.project.command.flow

import com.gabriel.lunala.project.command.handler.CommandHolder
import com.gabriel.lunala.project.command.impl.utils.PlanetCommand

class CommandRegistry {

    fun register(holder: CommandHolder) = holder.let {
        it.register(TestCommand())
        it.register(PlanetCommand())
    }.run { Unit }

}