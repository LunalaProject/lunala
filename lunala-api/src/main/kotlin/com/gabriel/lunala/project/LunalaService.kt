package com.gabriel.lunala.project

import arrow.Kind
import mu.KLogger

interface LunalaService<F> {

    val logger: KLogger

    fun start(): Kind<F, Unit>

    fun stop(): Kind<F, Unit>

}