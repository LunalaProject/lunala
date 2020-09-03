package com.gabriel.lunala.project.utils.client

import com.gabriel.lunala.project.utils.LunalaPermission
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.GuildChannel
import net.dv8tion.jda.api.entities.Member

object Permission {

    val CREATE_INSTANT_INVITE: LunalaPermission = LunalaPermission("CREATE_INSTANT_INVITE")
    val KICK_MEMBERS: LunalaPermission = LunalaPermission("KICK_MEMBERS")
    val BAN_MEMBERS: LunalaPermission = LunalaPermission("BAN_MEMBERS")
    val ADMINISTRATOR: LunalaPermission = LunalaPermission("ADMINISTRATOR")
    val MANAGE_CHANNEL: LunalaPermission = LunalaPermission("MANAGE_CHANNEL")
    val MANAGE_SERVER: LunalaPermission = LunalaPermission("MANAGE_SERVER")
    val MESSAGE_ADD_REACTION: LunalaPermission = LunalaPermission("MESSAGE_ADD_REACTION")
    val VIEW_AUDIT_LOGS: LunalaPermission = LunalaPermission("VIEW_AUDIT_LOGS")
    val PRIORITY_SPEAKER: LunalaPermission = LunalaPermission("PRIORITY_SPEAKER")
    val VIEW_GUILD_INSIGHTS: LunalaPermission = LunalaPermission("VIEW_GUILD_INSIGHTS")
    val VIEW_CHANNEL: LunalaPermission = LunalaPermission("VIEW_CHANNEL")
    val MESSAGE_READ: LunalaPermission = LunalaPermission("MESSAGE_READ")
    val MESSAGE_WRITE: LunalaPermission = LunalaPermission("MESSAGE_WRITE")
    val MESSAGE_TTS: LunalaPermission = LunalaPermission("MESSAGE_TTS")
    val MESSAGE_MANAGE: LunalaPermission = LunalaPermission("MESSAGE_MANAGE")
    val MESSAGE_EMBED_LINKS: LunalaPermission = LunalaPermission("MESSAGE_EMBED_LINKS")
    val MESSAGE_ATTACH_FILES: LunalaPermission = LunalaPermission("MESSAGE_ATTACH_FILES")
    val MESSAGE_HISTORY: LunalaPermission = LunalaPermission("MESSAGE_HISTORY")
    val MESSAGE_MENTION_EVERYONE: LunalaPermission = LunalaPermission("MESSAGE_MENTION_EVERYONE")
    val MESSAGE_EXT_EMOJI: LunalaPermission = LunalaPermission("MESSAGE_EXT_EMOJI")
    val VOICE_STREAM: LunalaPermission = LunalaPermission("VOICE_STREAM")
    val VOICE_CONNECT: LunalaPermission = LunalaPermission("VOICE_CONNECT")
    val VOICE_SPEAK: LunalaPermission = LunalaPermission("VOICE_SPEAK")
    val VOICE_MUTE_OTHERS: LunalaPermission = LunalaPermission("VOICE_MUTE_OTHERS")
    val VOICE_DEAF_OTHERS: LunalaPermission = LunalaPermission("VOICE_DEAF_OTHERS")
    val VOICE_MOVE_OTHERS: LunalaPermission = LunalaPermission("VOICE_MOVE_OTHERS")
    val VOICE_USE_VAD: LunalaPermission = LunalaPermission("VOICE_USE_VAD")
    val NICKNAME_CHANGE: LunalaPermission = LunalaPermission("NICKNAME_CHANGE")
    val NICKNAME_MANAGE: LunalaPermission = LunalaPermission("NICKNAME_MANAGE")
    val MANAGE_ROLES: LunalaPermission = LunalaPermission("MANAGE_ROLES")
    val MANAGE_PERMISSIONS: LunalaPermission = LunalaPermission("MANAGE_PERMISSIONS")
    val MANAGE_WEBHOOKS: LunalaPermission = LunalaPermission("MANAGE_WEBHOOKS")
    val MANAGE_EMOTES: LunalaPermission = LunalaPermission("MANAGE_EMOTES")
    val UNKNOWN: LunalaPermission = LunalaPermission("UNKNOWN")

    val ALL = listOf(
            CREATE_INSTANT_INVITE,
            KICK_MEMBERS,
            BAN_MEMBERS,
            ADMINISTRATOR,
            MANAGE_CHANNEL,
            MANAGE_SERVER,
            MESSAGE_ADD_REACTION,
            VIEW_AUDIT_LOGS,
            PRIORITY_SPEAKER,
            VIEW_GUILD_INSIGHTS,
            VIEW_CHANNEL,
            MESSAGE_READ,
            MESSAGE_WRITE,
            MESSAGE_TTS,
            MESSAGE_MANAGE,
            MESSAGE_EMBED_LINKS,
            MESSAGE_ATTACH_FILES,
            MESSAGE_HISTORY,
            MESSAGE_MENTION_EVERYONE,
            MESSAGE_EXT_EMOJI,
            VOICE_STREAM,
            VOICE_CONNECT,
            VOICE_SPEAK,
            VOICE_MUTE_OTHERS,
            VOICE_DEAF_OTHERS,
            VOICE_MOVE_OTHERS,
            VOICE_USE_VAD,
            NICKNAME_CHANGE,
            NICKNAME_MANAGE,
            MANAGE_ROLES,
            MANAGE_PERMISSIONS,
            MANAGE_WEBHOOKS,
            MANAGE_EMOTES,
            UNKNOWN
    )

}

fun Member.getLunalaPermissions(channel: GuildChannel): List<LunalaPermission> = com.gabriel.lunala.project.utils.client.Permission.ALL.filter {
    this.getPermissions(channel).contains(Permission.valueOf(it.name))
}