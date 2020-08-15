package com.gabriel.lunala.project.command

import com.gabriel.lunala.project.command.snapshot.ShardCommand
import com.gabriel.lunala.project.entity.Profile
import com.gabriel.lunala.project.entity.Server
import com.gabriel.lunala.project.utils.message.LunalaReply

interface CommandContext {

    val command: Command
    val label: String
    val profile: Profile
    val server: Server
    val args: List<String>
    val shard: ShardCommand

}