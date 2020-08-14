package com.gabriel.lunala.project.command.api

import com.gabriel.lunala.project.command.Command
import com.gabriel.lunala.project.command.CommandContext
import com.gabriel.lunala.project.command.snapshot.ShardCommand
import com.gabriel.lunala.project.utils.LunalaPermission

class CommandBuilder(private val labels: List<String>) {

    private val shards = mutableMapOf<List<String>, ShardCommand>()

    private var descriptionCallback: () -> String = { "My ultra cool description!" }
    private var examplesCallback: () -> List<String> = { emptyList() }

    @Suppress("unchecked_cast")
    fun <T : CommandContext> shard(vararg labels: String = emptyArray(), permissions: List<LunalaPermission> = emptyList(), callback: suspend T.() -> Unit) {
        shards[labels.toList()] = ShardCommand(labels.toList(), permissions, callback as suspend CommandContext.() -> Unit)
    }

    fun create(): Command = Command(
            labels,
            descriptionCallback(),
            examplesCallback(),
            shards
    )

}