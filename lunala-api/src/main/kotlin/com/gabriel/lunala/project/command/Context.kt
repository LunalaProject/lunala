package com.gabriel.lunala.project.command

import com.gabriel.lunala.project.entity.Profile
import com.gabriel.lunala.project.entity.Server

interface CommandContext {

    val label: String
    val profile: Profile
    val server: Server
    val args: List<String>
    val command: CommandDSL<*>

}