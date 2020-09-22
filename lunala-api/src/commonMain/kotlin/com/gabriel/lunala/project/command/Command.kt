package com.gabriel.lunala.project.command

import com.gabriel.lunala.project.command.snapshot.ShardCommand

class Command(
    val labels: List<String>,
    val description: String,
    val example: String,
    val shards: Map<List<String>, ShardCommand>
)