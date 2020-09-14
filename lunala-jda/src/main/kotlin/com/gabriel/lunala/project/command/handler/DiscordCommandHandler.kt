package com.gabriel.lunala.project.command.handler

import com.gabriel.lunala.project.Lunala
import com.gabriel.lunala.project.command.Command
import com.gabriel.lunala.project.command.exception.ExecutionException
import com.gabriel.lunala.project.command.exception.FailException
import com.gabriel.lunala.project.config.LunalaDiscordConfig
import com.gabriel.lunala.project.utils.client.sendMessage
import com.gabriel.lunala.project.utils.client.getLunalaPermissions
import com.gabriel.lunala.project.utils.client.getProfileOrCreate
import com.gabriel.lunala.project.utils.client.getServerOrCreate
import com.gabriel.lunala.project.utils.flaging.role
import com.gabriel.lunala.project.utils.message.LunaReply
import com.gabriel.lunala.project.utils.text.LevenshteinCalculator
import kotlinx.coroutines.*
import kotlinx.coroutines.future.await
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.inject

class DiscordCommandHandler: CommandHandler<DiscordCommandContext>, ListenerAdapter(), KoinComponent {

    private val holder: CommandHolder by inject()
    private val lunala: Lunala by inject()

    private val scope: CoroutineScope by inject()

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
                ?: return@launch

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
                client = event.jda,
                message = event.message,
                member = event.member!!,
                guild = event.guild,
                channel = event.channel
        ))
    }.run {
        Unit
    }

    override suspend fun dispatch(context: DiscordCommandContext): Unit = get<CoroutineScope>().launch {
        val hasPermission = context.member.getLunalaPermissions(context.channel).containsAll(context.shard.permissions)

        if (!hasPermission) {
            context.reply(LunaReply(
                    prefix = "\uD83D\uDEAB",
                    content = ", you need the following permissions to execute this command: `${context.shard.permissions.joinToString { it.name }}}`!",
                    mentionable = context.profile
            ))
            return@launch
        }

        if (context.profile.priority.isLower(context.shard.priority)) {
            context.reply(LunaReply(
                    prefix = "\uD83D\uDEAB",
                    content = ", you need to be a `${context.shard.priority.role}` or higher to execute this command!",
                    mentionable = context.profile
            ))
            return@launch
        }

        val exception: Throwable = context.shard.runCatching {
            callback.invoke(context)
        }.exceptionOrNull() ?: return@launch

        if (exception is FailException) exception.callback()

        val channel = context.member.user.runCatching {
            openPrivateChannel().submit().await()
        }.getOrNull() ?: return@launch

        val configuration: LunalaDiscordConfig by inject()

        channel.sendMessage(LunaReply(prefix = "\uD83D\uDD37", content = """
                B-beep boop! Apparently there was an error when you tried to execute the command `${context.label}`!
                            
                After some analyses, i figured out that the error was: `${exception::class.simpleName}: ${exception.message}`
                            
                Check if I have the correct permissions on your server, and try to execute the command
                one more time, and if the error persists, you can contact our administration team: ${configuration.discord}
    
                Thank you for the attention! :wink:
        """.replace("   ", ""), mentionable = context.profile)).queue()

        throw ExecutionException(context, exception)
    }.run { Unit }

    companion object {

        const val PREFIX = ">"

    }

}