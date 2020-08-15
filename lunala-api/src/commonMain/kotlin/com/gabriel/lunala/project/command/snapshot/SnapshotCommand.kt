package com.gabriel.lunala.project.command.snapshot

import com.gabriel.lunala.project.command.Command
import org.koin.core.KoinComponent

interface SnapshotCommand: KoinComponent {

    fun create(): Command

}