package com.gabriel.lunala.project.entity

import com.gabriel.lunala.project.entity.Server
import com.gabriel.lunala.project.table.LunalaServers
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class LunalaServer(id: EntityID<Long>): LongEntity(id), Server {

    companion object: LongEntityClass<LunalaServer>(LunalaServers)

    override val idLong: Long = id.value

}