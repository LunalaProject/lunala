package com.gabriel.lunala.project.service

import arrow.fx.IO
import com.gabriel.lunala.project.entity.LunalaServer
import com.gabriel.lunala.project.entity.Server
import com.gabriel.lunala.project.util.ServerService
import org.jetbrains.exposed.sql.transactions.transaction

class DefaultServerService: ServerService {

    override fun findById(id: Long): IO<Server> = IO {
        transaction {
            LunalaServer.findById(id) ?: error("Could not find user with id $id.")
        }
    }

    override fun findOrCreateById(id: Long): IO<Server> = IO {
        transaction {
            LunalaServer.findById(id) ?: LunalaServer.new(id) {}
        }
    }
}