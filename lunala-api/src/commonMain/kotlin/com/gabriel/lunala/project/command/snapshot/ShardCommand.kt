package com.gabriel.lunala.project.command.snapshot

import com.gabriel.lunala.project.command.CommandContext
import com.gabriel.lunala.project.utils.LunalaPermission
import com.gabriel.lunala.project.utils.flaging.Priority

class ShardCommand(
        val names: List<String>,
        val priority: Priority,
        val permissions: List<LunalaPermission>,
        val callback: suspend CommandContext.() -> Unit
)