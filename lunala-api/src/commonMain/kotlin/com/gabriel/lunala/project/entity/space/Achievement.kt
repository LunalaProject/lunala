package com.gabriel.lunala.project.entity.space

interface Achievement {

    val name: String
    val description: String
    val rarity: AchievementRarity

    enum class AchievementRarity(val id: String, val color: String) {

        COMMON("Common", "#31ad00"),
        UNCOMMON("Uncommon", "#07e7f2"),
        RARE("Rare", "#ff05e2"),
        EPIC("Epic", "#ac05ff"),
        LEGENDARY("Legendary", "#ff9f05")

    }

}