package com.gabriel.lunala.project.emojis

import com.gabriel.lunala.project.utils.Mentionable

sealed class Emote(val name: String, val id: Long, val animated: Boolean): Mentionable {

    object Check: Emote(
            "luna_check",
            752545534861901965,
            true
    )

    object Warn: Emote(
            "luna_warn",
            752545692832104568,
            true
    )


    override val asMention: String
        get() = if (animated) "<a:$name:$id>" else "<$name:$id>"

    override fun toString(): String = asMention

}