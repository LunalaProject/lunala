package com.gabriel.lunala.project

import org.koin.core.KoinComponent

abstract class Lunala {

    abstract suspend fun start()

    companion object: KoinComponent

}