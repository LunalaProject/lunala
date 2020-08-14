package com.gabriel.lunala.project.utils.message

import com.gabriel.lunala.project.utils.Mentionable

open class LunalaReply(val prefix: String, val content: String, val mentionable: Mentionable? = null) {

    open fun format(): String =
            "$prefix **|** ${mentionable?.asMention?.plus(" ").orEmpty()}$content"

    companion object {

        operator fun invoke(content: String): LunalaReply =
                SimpleReply(content)

        class SimpleReply(content: String): LunalaReply("Empty", content, null) {

            override fun format(): String = this.content

        }

    }

}