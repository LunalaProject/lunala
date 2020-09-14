package com.gabriel.lunala.project

import com.gabriel.lunala.project.lifecycle.ILifecycle
import org.koin.core.KoinComponent

abstract class Lunala: ILifecycle {

    companion object: KoinComponent

}