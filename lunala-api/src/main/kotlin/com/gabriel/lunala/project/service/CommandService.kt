package com.gabriel.lunala.project.service

import arrow.Kind
import arrow.fx.ForIO
import com.gabriel.lunala.project.command.CommandRepository

interface CommandService {

    val repository: CommandRepository

    fun start(): Kind<ForIO, Unit>

    fun stop(): Kind<ForIO, Unit>

}