package com.gabriel.lunala.project.module

interface LunalaModuleController {

    fun load(module: LunalaModule)

    fun parse(name: String?): LunalaModule?

    fun unload(module: LunalaModule)

}