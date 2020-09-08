package com.gabriel.lunala.project.command.api

import com.gabriel.lunala.project.command.Command
import com.gabriel.lunala.project.command.CommandContext
import com.gabriel.lunala.project.command.snapshot.ShardCommand
import com.gabriel.lunala.project.utils.CommandDslMarker
import com.gabriel.lunala.project.utils.LunalaPermission
import com.gabriel.lunala.project.utils.flaging.Priority

class CommandBuilder(private val labels: List<String>) {

    val shards = mutableMapOf<List<String>, ShardCommand>()

    private var descriptionCallback: () -> String = { "My ultra cool description!" }
    private var examplesCallback: () -> List<String> = { listOf("") }

    @Suppress("unchecked_cast")
    @CommandDslMarker
    fun <T : CommandContext> shard(vararg labels: String = emptyArray(), priority: Priority = Priority.LOW, permissions: List<LunalaPermission> = emptyList(), callback: suspend T.() -> Unit) {
        shards[labels.toList()] = ShardCommand(labels.toList(), priority, permissions, callback as suspend CommandContext.() -> Unit)
    }

    fun description(callback: () -> String) {
        descriptionCallback = callback
    }

    fun examples(callback: () -> List<String>) {
        examplesCallback = callback
    }

    fun create(): Command = Command(
            labels,
            descriptionCallback(),
            examplesCallback(),
            shards
    )

}