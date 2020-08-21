package com.gabriel.lunala.project.utils.message

import com.gabriel.lunala.project.image.Image
import com.gabriel.lunala.project.utils.Mentionable

open class LunaReply(
        val prefix: String,
        val content: String,
        val mentionable: Mentionable? = null,
        val image: Image? = null
) {

    open fun format(): String =
            "$prefix **|** ${mentionable?.asMention?.plus(" ").orEmpty()}$content"

    companion object {

        operator fun invoke(content: String): LunaReply =
                LunaReply("Empty", content)

    }

}