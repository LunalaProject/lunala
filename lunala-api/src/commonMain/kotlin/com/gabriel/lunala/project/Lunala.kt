package com.gabriel.lunala.project

import com.gabriel.lunala.project.environment.ClientEnvironment
import org.koin.core.KoinComponent

abstract class Lunala(val environment: ClientEnvironment) {

    abstract fun start()

    abstract fun stop()

    companion object: KoinComponent

}