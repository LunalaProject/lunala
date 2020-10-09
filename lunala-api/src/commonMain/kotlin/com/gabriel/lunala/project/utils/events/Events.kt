package com.gabriel.lunala.project.utils.events

interface EventHolder {

    suspend fun onEvent(event: Any)

}

interface LunalaEventManager {

    fun register(listener: Any)

    fun unregister(listener: Any)

}