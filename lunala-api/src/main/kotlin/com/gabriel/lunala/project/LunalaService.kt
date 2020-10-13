package com.gabriel.lunala.project

import arrow.Kind
import com.gabriel.lunala.project.util.LunalaDiscordConfig

interface LunalaService<F> {

    val config: LunalaDiscordConfig

    fun start(): Kind<F, Unit>

    fun stop(): Kind<F, Unit>

}