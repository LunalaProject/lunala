package com.gabriel.lunala.project.command.utils

import com.gabriel.lunala.project.command.Command
import com.gabriel.lunala.project.command.CommandBuilder
import com.gabriel.lunala.project.command.CommandContext
import com.gabriel.lunala.project.command.exception.FailException
import com.gabriel.lunala.project.utils.CommandDslMarker

@CommandDslMarker
inline fun command(vararg labels: String, block: CommandBuilder.() -> Unit): Command =
        CommandBuilder(labels.toList()).apply(block).create()

@CommandDslMarker
fun CommandContext.fail(callback: suspend () -> Unit): Nothing =
        throw FailException(this, callback)