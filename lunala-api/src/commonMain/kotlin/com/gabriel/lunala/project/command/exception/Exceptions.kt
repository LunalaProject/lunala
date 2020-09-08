package com.gabriel.lunala.project.command.exception

import com.gabriel.lunala.project.command.CommandContext

data class FailException(
        val context: CommandContext,
        val callback: suspend () -> Unit
): RuntimeException()

data class ExecutionException(
        val context: CommandContext,
        val exception: Throwable
): RuntimeException(exception)