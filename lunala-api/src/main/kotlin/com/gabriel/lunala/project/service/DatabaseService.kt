package com.gabriel.lunala.project.service

import arrow.Kind
import arrow.fx.ForIO

interface DatabaseService {

    fun connect(): Kind<ForIO, Unit>

    fun createTables(): Kind<ForIO, Unit>

}