package com.gabriel.lunala.project.command.snapshot

import com.gabriel.lunala.project.command.Command

interface SnapshotCommand {

    fun create(): Command

}