package com.gabriel.lunala.project.command.flow

import com.gabriel.lunala.project.command.handler.CommandHolder
import com.gabriel.lunala.project.command.impl.TestCommand
import com.gabriel.lunala.project.command.impl.utils.PlanetCommand
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

class CommandRegistry {

    fun register(holder: CommandHolder) = holder.let {
        it.register(TestCommand())
        it.register(PlanetCommand())
    }.run { Unit }

}