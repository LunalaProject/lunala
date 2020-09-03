package com.gabriel.lunala.project.sql

import com.gabriel.lunala.project.entity.Server
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object LunalaServers: LongIdTable()

class LunalaServer(id: EntityID<Long>): LongEntity(id), Server {

    companion object: LongEntityClass<LunalaServer>(LunalaServers)

    override val idLong: Long = id.value

}