package com.gabriel.lunala.project.database

import com.gabriel.lunala.project.Lunala

abstract class LunalaDatabase(val lunala: Lunala) {

    abstract suspend fun connect()

    abstract suspend fun createTables()

}