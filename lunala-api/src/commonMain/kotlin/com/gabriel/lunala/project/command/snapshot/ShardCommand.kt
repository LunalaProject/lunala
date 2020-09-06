package com.gabriel.lunala.project.command.snapshot

import com.gabriel.lunala.project.command.CommandContext
import com.gabriel.lunala.project.utils.LunalaPermission
import com.gabriel.lunala.project.utils.flaging.Flag

class ShardCommand(
        val names: List<String>,
        val flag: Flag,
        val permissions: List<LunalaPermission>,
        val callback: suspend CommandContext.() -> Unit
)