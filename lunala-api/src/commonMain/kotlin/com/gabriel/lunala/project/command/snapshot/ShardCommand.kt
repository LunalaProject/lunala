package com.gabriel.lunala.project.command.snapshot

import com.gabriel.lunala.project.command.CommandContext
import com.gabriel.lunala.project.command.type.CommandExecutionBlock
import com.gabriel.lunala.project.utils.LunalaPermission

class ShardCommand(
        val names: List<String>,
        val permissions: List<LunalaPermission>,
        val callback: CommandExecutionBlock<CommandContext>
)