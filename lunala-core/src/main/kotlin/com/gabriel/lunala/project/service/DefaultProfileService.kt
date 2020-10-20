package com.gabriel.lunala.project.service

import arrow.fx.IO
import com.gabriel.lunala.project.entity.LunalaProfile
import com.gabriel.lunala.project.entity.Profile
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class DefaultProfileService: ProfileService {

    override fun findById(id: Long): IO<Profile> = IO {
        newSuspendedTransaction {
            LunalaProfile.findById(id) ?: error("Could not find user with id $id.")
        }
    }

    override fun findOrCreateById(id: Long): IO<Profile> = IO {
        newSuspendedTransaction {
            LunalaProfile.findById(id) ?: LunalaProfile.new(id) {}
        }
    }
}