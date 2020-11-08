package com.gabriel.lunala.project.service

import com.gabriel.lunala.project.command.CommandDSL
import kotlinx.coroutines.Job

interface PrototypeService {

    val commands: MutableList<CommandDSL<*>>
    val listeners: MutableList<Job>

    suspend fun start()

    fun getCommand(name: String, aliases: Boolean = true): CommandDSL<*>
}