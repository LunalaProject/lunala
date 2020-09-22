package com.gabriel.lunala.project.commands

import com.gabriel.lunala.project.command.Command
import com.gabriel.lunala.project.command.snapshot.SnapshotCommand
import com.gabriel.lunala.project.command.utils.command
import com.gabriel.lunala.project.command.utils.fail
import com.gabriel.lunala.project.utils.local

class NotifyCommand: SnapshotCommand {

    override fun create(): Command = command("notify", "updates") {
        description {
            "Add the <@$roleId> to receive my awesome updates!"
        }

        local {
            val role = guild.getRoleById(roleId) ?: error("Updates role not found.")

            if (!member.roles.contains(role)) {
                guild.addRoleToMember(member, role).queue()
                fail("\uD83C\uDF67", "Now you're going to receive my updates before anyone else! Thank you so much!", profile)
            } else {
                guild.removeRoleFromMember(member, role).queue()
                fail("\uD83C\uDF68", "So you don't want to receive my updates anymore... It's okay, I understand that getting pinged can be annoying sometimes!", profile)
            }
        }
    }

    companion object {

        const val roleId: Long = 757632205156647082L

    }

}
