package com.gabriel.lunala.project.utils

import com.gabriel.lunala.project.utils.Permission.PERMISSIONS
import com.gitlab.kordlib.common.entity.Permission
import com.gitlab.kordlib.core.entity.Member
import com.gitlab.kordlib.core.entity.channel.GuildChannel

object Permission {

    val CreateInstantInvite: LunalaPermission = LunalaPermission("CreateInstantInvite")
    val KickMembers: LunalaPermission = LunalaPermission("KickMembers")
    val BanMembers: LunalaPermission = LunalaPermission("BanMembers")
    val Administrator: LunalaPermission = LunalaPermission("Administrator")
    val ManageChannels: LunalaPermission = LunalaPermission("ManageChannels")
    val ManageGuild: LunalaPermission = LunalaPermission("ManageGuild")
    val AddReactions: LunalaPermission = LunalaPermission("AddReactions")
    val ViewAuditLog: LunalaPermission = LunalaPermission("ViewAuditLog")
    val ViewChannel: LunalaPermission = LunalaPermission("ViewChannel")
    val SendMessages: LunalaPermission = LunalaPermission("SendMessages")
    val SendTTSMessages: LunalaPermission = LunalaPermission("SendTTSMessages")
    val ManageMessages: LunalaPermission = LunalaPermission("ManageMessages")
    val EmbedLinks: LunalaPermission = LunalaPermission("EmbedLinks")
    val AttachFiles: LunalaPermission = LunalaPermission("AttachFiles")
    val ReadMessageHistory: LunalaPermission = LunalaPermission("ReadMessageHistory")
    val MentionEveryone: LunalaPermission = LunalaPermission("MentionEveryone")
    val UseExternalEmojis: LunalaPermission = LunalaPermission("UseExternalEmojis")
    val ViewGuildInsights: LunalaPermission = LunalaPermission("ViewGuildInsights")
    val Connect: LunalaPermission = LunalaPermission("Connect")
    val Speak: LunalaPermission = LunalaPermission("Speak")
    val MuteMembers: LunalaPermission = LunalaPermission("MuteMembers")
    val DeafenMembers: LunalaPermission = LunalaPermission("DeafenMembers")
    val MoveMembers: LunalaPermission = LunalaPermission("MoveMembers")
    val UseVAD: LunalaPermission = LunalaPermission("UseVAD")
    val PrioritySpeaker: LunalaPermission = LunalaPermission("PrioritySpeaker")
    val ChangeNickname: LunalaPermission = LunalaPermission("ChangeNickname")
    val ManageNicknames: LunalaPermission = LunalaPermission("ManageNicknames")
    val ManageRoles: LunalaPermission = LunalaPermission("ManageRoles")
    val ManageWebhooks: LunalaPermission = LunalaPermission("ManageWebhooks")
    val ManageEmojis: LunalaPermission = LunalaPermission("ManageEmojis")
    val All: LunalaPermission = LunalaPermission("All")

    val PERMISSIONS = listOf(
            CreateInstantInvite,
            KickMembers,
            BanMembers,
            Administrator,
            ManageChannels,
            ManageGuild,
            AddReactions,
            ViewAuditLog,
            ViewChannel,
            SendMessages,
            SendTTSMessages,
            ManageMessages,
            EmbedLinks,
            AttachFiles,
            ReadMessageHistory,
            MentionEveryone,
            UseExternalEmojis,
            ViewGuildInsights,
            Connect,
            Speak,
            MuteMembers,
            DeafenMembers,
            MoveMembers,
            UseVAD,
            PrioritySpeaker,
            ChangeNickname,
            ManageNicknames,
            ManageRoles,
            ManageWebhooks,
            ManageEmojis,
            All
    )

}

suspend fun Member.getLunalaPermissions(channel: GuildChannel): List<LunalaPermission> = PERMISSIONS.filter {
    channel.getEffectivePermissions(id).contains(Permission.valueOf(it.name))
}