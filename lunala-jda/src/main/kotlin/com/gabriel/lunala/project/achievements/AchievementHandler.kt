package com.gabriel.lunala.project.achievements

import com.gabriel.lunala.project.Lunala
import com.gabriel.lunala.project.entity.Profile
import com.gabriel.lunala.project.entity.extra.Achievement
import com.gabriel.lunala.project.sql.data.LunalaAchievements
import com.gabriel.lunala.project.utils.client.sendMessage
import com.gabriel.lunala.project.utils.embed.embed
import com.gabriel.lunala.project.utils.client.standardTransaction
import com.gabriel.lunala.project.utils.message.LunaReply
import kotlinx.coroutines.future.await
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.sharding.ShardManager
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.koin.core.KoinComponent
import org.koin.core.get
import org.slf4j.Logger
import java.awt.Color
import java.time.Instant

class AchievementHandler: KoinComponent {

    suspend fun send(profile: Profile, channel: MessageChannel, achievement: Achievement) {
        val user = get<ShardManager>().retrieveUserById(profile.idLong).submit().await()
                ?: return

        val exists = get<Lunala>().standardTransaction {
            LunalaAchievements.select {
                (LunalaAchievements.userId eq profile.idLong) and (LunalaAchievements.achievement eq achievement.name)
            }.count() != 0L
        }

        if (exists) return get<Logger>().info("User won an achievement but already have it")

        get<Lunala>().standardTransaction {
            LunalaAchievements.insert {
                it[userId] = profile.idLong
                it[LunalaAchievements.achievement] = achievement.name
                it[date] = Instant.now()
            }
        }

        val message = embed {

            header {

                title = ":mailbox: New Achievement"
                description = ":tada: **•** You unlocked a brand new achievement!"

            }

            fieldset {
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
            }


            images {
                color = Color.decode(achievement.rarity.color)
                thumbnail = user.effectiveAvatarUrl
            }

            footer {
                timestamp = Instant.now()
            }
        }

        kotlin.runCatching {
            channel.sendMessage(LunaReply(
                    ":tada:",
                    "won the achievement `${achievement.name}`, congratulations!",
                    mentionable = profile
            ))

            user.openPrivateChannel().submit().await().sendMessage(message)
        }.exceptionOrNull()?.run {
            channel.sendMessage(message).queue()
        }
    }

}