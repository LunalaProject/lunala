package com.gabriel.lunala.project.command.registry

import com.gabriel.lunala.project.command.handler.CommandHolder
import com.gabriel.lunala.project.command.impl.utils.PlanetCommand
import com.gabriel.lunala.project.ex.doAlso

class CommandRegistry {

    fun register(holder: CommandHolder): Unit = holder.doAlso {
        it.register(PlanetCommand())
    }

}