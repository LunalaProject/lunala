package com.gabriel.lunala.project.creative

import com.gabriel.lunala.project.assets.LunalaAssets
import com.gabriel.lunala.project.image.Image

/**
 *
 *
 * @param name Drawing's name
 * @param id Drawing's id
 */
abstract class Drawing(
        val name: String,
        val id: Int
) {

    abstract fun getImage(): Image

}