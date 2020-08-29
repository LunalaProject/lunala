package com.gabriel.lunala.project.achievements

import com.gabriel.lunala.project.entity.Profile
import com.gabriel.lunala.project.entity.extra.Achievement
import com.gabriel.lunala.project.table.LunalaAchievements
import com.gabriel.lunala.project.utils.embed
import com.gabriel.lunala.project.utils.message.LunaReply
import com.gabriel.lunala.project.utils.toSnowflake
import com.gitlab.kordlib.common.entity.Snowflake
import com.gitlab.kordlib.core.Kord
import com.gitlab.kordlib.core.behavior.channel.createMessage
import com.gitlab.kordlib.core.entity.channel.MessageChannel
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.KoinComponent
import org.koin.core.get
import org.slf4j.Logger
import java.awt.Color
import java.time.Instant

class AchievementHandler: KoinComponent {

    suspend fun send(profile: Profile, channel: MessageChannel, achievement: Achievement) {
        val user = get<Kord>().getUser(profile.idLong.toSnowflake())
                ?: return

        val exists = transaction {
            LunalaAchievements.select {
                (LunalaAchievements.userId eq profile.idLong) and (LunalaAchievements.achievement eq achievement.name)
            }.count() != 0L
        }

        if (exists) return get<Logger>().info("User won an achievement but already have it")

        transaction {
            LunalaAchievements.insert {
                it[userId] = profile.idLong
                it[LunalaAchievements.achievement] = achievement.name
                it[date] = Instant.now()
            }
        }

        val message = embed {
            title = ":mailbox: New Achievement"
            description = ":tada: **•** You unlocked a brand new achievement!"

            field {
                name = ":paperclip: Name"
                value = achievement.name
                inline = true
            }

            field {
                name = ":calendar_spiral: Rarity"
                value = achievement.rarity.id
                inline = true
            }

            field {
                name = ":newspaper: Description"
                value = achievement.description
                inline = false
            }

            thumbnail {
                url = user.avatar.url
            }

            color = Color.decode(achievement.rarity.color)
            timestamp = Instant.now()
        }

        kotlin.runCatching {
            channel.createMessage(LunaReply(
                    ":tada:",
                    "won the achievement `${achievement.name}`, congratulations!",
                    mentionable = profile
            ).format())
            user.getDmChannel().createMessage {
                embed = message
            }
        }.exceptionOrNull()?.run {
            channel.createMessage {
                content = user.mention
                embed = message
            }
        }
    }

}