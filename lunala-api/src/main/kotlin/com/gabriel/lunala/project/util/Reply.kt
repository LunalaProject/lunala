package com.gabriel.lunala.project.util

open class LunalaReply(
    var prefix: String,
    var message: String,
    var mentions: Boolean = true
) {

    open fun compile(target: LunalaProfile? = null): String = "$prefix **|** ${if (target != null && mentions) target.mention.plus(" ") else "" }$message"

    companion object

}