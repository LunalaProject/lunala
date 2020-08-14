package com.gabriel.lunala.project.command

import com.gabriel.lunala.project.command.snapshot.ShardCommand

class Command(
    val labels: List<String>,
    val description: String,
    val examples: List<String>,
    val shards: Map<List<String>, ShardCommand>
) {

    val mainLabel = labels.getOrNull(0)
            ?: error("Command labels are empty")

}