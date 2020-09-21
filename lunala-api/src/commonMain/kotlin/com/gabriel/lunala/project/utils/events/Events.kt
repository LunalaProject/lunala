package com.gabriel.lunala.project.utils.events

interface EventHolder {

    suspend fun onEvent(event: Any)

}