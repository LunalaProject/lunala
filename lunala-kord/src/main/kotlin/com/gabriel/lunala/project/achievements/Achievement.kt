package com.gabriel.lunala.project.achievements

import com.gabriel.lunala.project.entity.space.Achievement

object Achievements {

    val THIS_IS_WHERE_IT_BEGINS = achievement(
            "This is where it begins",
            "You started your amazing journey! Many planets are waiting for you, but that doesn't mean you have to wait too, go on!",
            Achievement.AchievementRarity.COMMON
    )

    val ALL_READY_FOR_THE_LAUNCHING = achievement(
            "All ready for the launching",
            "You are inside of a spaceship! Launching in 3.. 2..",
            Achievement.AchievementRarity.COMMON
    )

    val DIFFERENT_PLANETS_DIFFERENT_HORIZONS = achievement(
            "Different planet different horizons",
            "Congratulations! Let's party! You went to another planet!",
            Achievement.AchievementRarity.UNCOMMON
    )

    val OVERHEAT = achievement(
            "Overheat",
            "You tried to travel to the Sun, but Starlight's team could control your spaceship before.",
            Achievement.AchievementRarity.UNCOMMON
    )


    val achievements = mutableListOf(
            THIS_IS_WHERE_IT_BEGINS,
            ALL_READY_FOR_THE_LAUNCHING,
            DIFFERENT_PLANETS_DIFFERENT_HORIZONS,
            OVERHEAT
    )

}

data class SimpleAchievement(override val name: String, override val description: String, override val rarity: Achievement.AchievementRarity): Achievement

private fun achievement(name: String, description: String, rarity: Achievement.AchievementRarity): Achievement =
        SimpleAchievement(name, description, rarity)