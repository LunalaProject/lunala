package com.gabriel.lunala.project.service

import arrow.fx.IO
import com.gabriel.lunala.project.entity.LunalaServer
import com.gabriel.lunala.project.entity.Server
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class DefaultServerService: ServerService {

    override fun findById(id: Long): IO<Server> = IO {
        newSuspendedTransaction {
            LunalaServer.findById(id) ?: error("Could not find server with id $id.")
        }
    }

    override fun findOrCreateById(id: Long): IO<Server> = IO {
        newSuspendedTransaction {
            LunalaServer.findById(id) ?: LunalaServer.new(id) {}
        }
    }
}