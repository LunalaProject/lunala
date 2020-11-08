package com.gabriel.lunala.project.command

import com.gabriel.lunala.project.entity.Guild
import com.gabriel.lunala.project.entity.User

interface CommandContext {

    val label: String
    val profile: User
    val server: Guild
    val args: List<String>
    val command: CommandDSL<*>

}

data class FailException(
        val callback: suspend () -> Unit
): RuntimeException()