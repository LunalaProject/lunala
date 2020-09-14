package com.gabriel.lunala.project.route

import com.gabriel.lunala.project.Lunala
import com.gabriel.lunala.project.emojis.Emote
import com.gabriel.lunala.project.entity.Profile
import com.gabriel.lunala.project.utils.client.sendMessage
import com.gabriel.lunala.project.utils.message.LunaReply
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import net.dv8tion.jda.api.entities.TextChannel
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.concurrent.ThreadLocalRandom
import kotlin.math.roundToInt
import kotlin.random.Random

class DefaultBattleRoute: BattleRoute, KoinComponent {

    private val scope: CoroutineScope by inject()
    private val lunala: Lunala by inject()

    override suspend fun init(profile: Profile, channel: TextChannel) {
        channel.sendMessage(LunaReply(
                "\uD83D\uDEF8",
                "You're now in a battle against an level **${(profile.level * 1.5).roundToInt()}** alien gang!",
                profile
        )).queue()

        val enemies = (profile.tripulation * 0.7).let { Random.nextInt((it * 0.3).roundToInt(), (it * 1.0).roundToInt()) }
        val won = chance(((profile.tripulation - enemies) + ThreadLocalRandom.current().nextInt(5, 20) ) * 3)

        delay(5000L)

        if (won) {
            channel.sendMessage(LunaReply("${Emote.Check}", "Congratulations! You won a battle against **$enemies** aliens, and as a reward, the **${(enemies * 0.3)}** alive aliens will join your tripulation!", profile)).queue()
        } else {
            channel.sendMessage(LunaReply("${Emote.Warn}", "You lost the battle to **$enemies** aliens, but don't worry! We rescued you and you're at ${"Earth"} and safe.", profile)).queue()
        }
    }

    private fun chance(int: Int): Boolean =
            ThreadLocalRandom.current().nextInt(100) < int

}