package com.gabriel.lunala.project.command.handler

import com.gabriel.lunala.project.Lunala
import com.gabriel.lunala.project.command.Command
import com.gabriel.lunala.project.command.exception.FailException
import com.gabriel.lunala.project.utils.luna.getProfileOrCreate
import com.gabriel.lunala.project.utils.luna.getServerOrCreate
import com.gabriel.lunala.project.utils.message.LunaReply
import com.gabriel.lunala.project.utils.text.LevenshteinCalculator
import kotlinx.coroutines.*
import kotlinx.coroutines.future.await
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.sharding.ShardManager
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.inject

class DiscordCommandHandler(shardManager: ShardManager): CommandHandler<DiscordCommandContext>, ListenerAdapter(), KoinComponent {

    private val holder: CommandHolder by inject()
    private val lunala: Lunala by inject()

    private val scope: CoroutineScope by inject()

    init {
        shardManager.addEventListener(this)
    }

    override fun onMessageReceived(event: MessageReceivedEvent) {
        println("kk")
    }

    override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) = scope.launch {
        if (!event.message.contentRaw.startsWith(PREFIX)) return@launch
        if (event.message.contentRaw.length == PREFIX.length) return@launch

        val content = event.message.contentRaw
                .substring(PREFIX.length)
                .trim()
                .split(" ")

        val args = content.drop(1).toMutableList()

        val command = get<CommandHolder>().commands.filter {
            it.key.contains(content.firstOrNull()?.toLowerCase())
        }.map { it.value }.firstOrNull()
                ?: return@launch event.message.channel.sendMessage(similarest(content.firstOrNull()!!.toLowerCase())?.labels?.joinToString { it }
                        ?: "nada gg").run { Unit }

        val shard = command.shards.filter {
            it.key.contains(content.drop(1).getOrNull(0)?.toLowerCase())
        }.map {
            it.value
        }.firstOrNull() ?: (command.shards[emptyList()] ?: error("Main shard was not defined."))

        if (shard.names.isNotEmpty())
            args.removeAt(0)

        val profile = lunala.getProfileOrCreate(event.message.author.idLong)
        val server = lunala.getServerOrCreate(event.guild.idLong)

        dispatch(DiscordCommandContext(
                command = command,
                label = content[0],
                profile = profile,
                server = server,
                args = args,
                shard = shard,
                message = event.message,
                member = event.member!!,
                guild = event.guild,
                channel = event.channel
        ))
    }.run {
        Unit
    }

    override suspend fun dispatch(context: DiscordCommandContext) = get<CoroutineScope>().launch {
        val hasPermission = true

        if (!hasPermission) {
            context.reply(LunaReply(
                    prefix = ":no_entry_sign:",
                    content = ", you need the following permissions to execute this command: `${context.shard.permissions.joinToString { it.name }}}`!",
                    mentionable = context.profile
            ))
            return@launch
        }

        val exception: Throwable = context.shard.runCatching {
            callback.invoke(context)
        }.exceptionOrNull() ?: return@launch

        if (exception is FailException) exception.callback()

        exception.printStackTrace()

        context.member.user.openPrivateChannel().submit().await().runCatching {
            /**
            createMessage(LunaReply(
                    """B-beep boop! Apparently there was an error when you tried to execute the command `${context.label}`!
                        
                    `${exception.message}`
                        
                    Check if I have the correct permissions on your server, and try to execute the command
                    one more time, and if the error persists, you can contact our administration team: discord.gg/Error404

                    Thank you for the attention! :wink:
                    """.trimIndent()
            ).format())
            */
        }

    }.run { Unit }

    private fun similarest(input: String): Command? = holder.commands.values.associateBy {
        LevenshteinCalculator.levenshtein(input, it.labels[0])
    }.maxBy { it.key }?.value

    companion object {

        const val PREFIX = ">"

    }

}