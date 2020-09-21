package com.gabriel.lunala.project.module

import kotlinx.coroutines.Dispatchers
import java.io.File
import kotlin.coroutines.CoroutineContext

open class DiscordModule(
        name: String,
        enabled: Boolean = false,
        coroutineContext: CoroutineContext = Dispatchers.Default,
        internal var file: File? = null
): LunalaModule(name, enabled, coroutineContext) {

}