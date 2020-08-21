package com.gabriel.lunala.project.assets

sealed class LunalaAssets(
        val path: String
) {

    object FanArts: LunalaAssets("/fanarts")

    object Images: LunalaAssets("/images")

    object Fonts: LunalaAssets("/fonts")

}