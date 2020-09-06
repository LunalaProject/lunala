package com.gabriel.lunala.project.command.api

import com.gabriel.lunala.project.command.Command
import com.gabriel.lunala.project.command.CommandContext
import com.gabriel.lunala.project.command.snapshot.ShardCommand
import com.gabriel.lunala.project.utils.LunalaPermission
import com.gabriel.lunala.project.utils.flaging.Flag

class CommandBuilder(private val labels: List<String>) {

    private val shards = mutableMapOf<List<String>, ShardCommand>()

    private var descriptionCallback: () -> String = { "My ultra cool description!" }
    private var examplesCallback: () -> List<String> = { listOf("") }

    fun description(callback: () -> String) {
        descriptionCallback = callback
    }

    fun examples(callback: () -> List<String>) {
        examplesCallback = callback
    }

    @Suppress("unchecked_cast")
    fun <T : CommandContext> shard(vararg labels: String = emptyArray(), flag: Flag = Flag.LOW, permissions: List<LunalaPermission> = emptyList(), callback: suspend T.() -> Unit) {
        shards[labels.toList()] = ShardCommand(labels.toList(), flag, permissions, callback as suspend CommandContext.() -> Unit)
    }

    fun create(): Command = Command(
            labels,
            descriptionCallback(),
            examplesCallback(),
            shards
    )

}