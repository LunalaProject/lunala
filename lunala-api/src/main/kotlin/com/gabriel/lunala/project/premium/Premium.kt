package com.gabriel.lunala.project.premium

enum class Premium(val i18n: String, val cost: Int, val coinsMultiplier: Float) {

    NONE("plans.none.name", 0, 1f),
    STAR("plans.star.name", 5, 1.5f),
    SUPERNOVA("plans.supernova.name", 10, 2f),
    BLACK_HOLE("plans.blackhole.name", 15, 2.5f);

}