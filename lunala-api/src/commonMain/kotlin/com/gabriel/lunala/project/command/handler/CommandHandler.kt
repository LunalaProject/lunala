package com.gabriel.lunala.project.command.handler

import com.gabriel.lunala.project.command.CommandContext

interface CommandHandler<T : CommandContext> {

    suspend fun dispatch(context: T)

}