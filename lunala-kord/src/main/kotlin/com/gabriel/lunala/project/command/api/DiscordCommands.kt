package com.gabriel.lunala.project.command.api

import com.gabriel.lunala.project.command.Command
import com.gabriel.lunala.project.command.CommandContext
import com.gabriel.lunala.project.command.exception.FailException
import com.gabriel.lunala.project.command.handler.DiscordCommandContext
import com.gabriel.lunala.project.command.snapshot.SnapshotCommand

inline fun command(vararg labels: String, block: CommandBuilder.() -> Unit): Command =
        CommandBuilder(labels.toList()).apply(block).create()

fun CommandContext.fail(callback: () -> Unit): Nothing =
        throw FailException(callback)
