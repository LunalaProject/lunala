package com.gabriel.lunala.project.module

import kotlinx.coroutines.Dispatchers
import java.io.File
import kotlin.coroutines.CoroutineContext

open class DiscordModule(
        name: String,
        val file: File
): LunalaModule(name, false, Dispatchers.Default)