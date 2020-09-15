package com.gabriel.lunala.project.route

import com.gabriel.lunala.project.Lunala
import com.gabriel.lunala.project.emojis.Emote
import com.gabriel.lunala.project.entity.Profile
import com.gabriel.lunala.project.utils.client.sendMessage
import com.gabriel.lunala.project.utils.client.standardTransaction
import com.gabriel.lunala.project.utils.message.LunaReply
import com.gabriel.lunala.project.utils.observer.onReaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.future.await
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.TextChannel
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.concurrent.ThreadLocalRandom
import kotlin.math.roundToInt
import kotlin.random.Random

class DefaultBattleRoute: BattleRoute, KoinComponent {

    private val scope: CoroutineScope by inject()
    private val lunala: Lunala by inject()

    override suspend fun init(profile: Profile, member: Member, channel: TextChannel) {
        channel.sendMessage(LunaReply(
                "\uD83D\uDEF8",
                "You're now in a battle against an level **${(profile.level * 1.5).roundToInt()}** alien gang!",
                profile
        )).queue()

        // TODO (profile.tripulation * 0.7).let { Random.nextDouble(it / 2.1, it * 2).toInt() }
        val enemies = 2
        val won = chance(((profile.tripulation - enemies) + ThreadLocalRandom.current().nextInt(5, 20) ) * 3).or(true)

        delay(5000L)

        if (won) {
            lunala.standardTransaction {
                profile.tripulation = ((enemies * 0.3).roundToInt())
            }

            val pendingJobs = mutableListOf<Job>()

            channel.sendMessage(LunaReply("${Emote.Check}", "Congratulations! You won a battle against **$enemies** aliens, do you want to recruit them to your tripulation or kill and sell their heads?", profile)).submit().await().also {
                it.addReaction("\uD83D\uDE80").queue()
                it.addReaction("⚔️").queue()

                pendingJobs.add(it.onReaction(member.user) {
                    if (reactionEmote.isEmote) return@onReaction

                    println("Codepoints: ${reactionEmote.asCodepoints}")

                    if (reactionEmote.asCodepoints == "U+1f680") {
                        channel.sendMessage(LunaReply(":rocket:", "You recruited **${enemies * 0.3}** aliens to your tripulation, now it has **${profile.tripulation}** members!", profile)).queue()
                        lunala.standardTransaction {
                            profile.tripulation += ((enemies * 0.3).roundToInt())
                        }
                    } else if (reactionEmote.asCodepoints == "U+2694U+fe0f") {
                        val coins = (((enemies * 0.3).roundToInt()) * ThreadLocalRandom.current().nextInt(100, 150)).toLong()

                        channel.sendMessage(LunaReply(":moneybag:", "You killed the remaining aliens and got **$coins** coins!", profile)).queue()
                        lunala.standardTransaction {
                            profile.money += coins
                        }
                    }

                    pendingJobs.forEach { it.cancel() }
                })
            }
        } else {
            lunala.standardTransaction {
                profile.travelingTime = System.currentTimeMillis() + 600000L
            }
            channel.sendMessage(LunaReply("${Emote.Warn}", "You lost the battle to **$enemies** aliens, but don't worry! We're going back to the Earth, you will have to wait 10 minutes to to any action.", profile)).queue()
        }
    }

    private fun chance(int: Int): Boolean =
            ThreadLocalRandom.current().nextInt(100) < int

}