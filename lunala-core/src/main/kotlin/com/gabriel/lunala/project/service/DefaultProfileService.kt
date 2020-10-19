package com.gabriel.lunala.project.service

import arrow.fx.IO
import arrow.fx.extensions.fx
import com.gabriel.lunala.project.entity.LunalaProfile
import com.gabriel.lunala.project.entity.Profile
import com.gabriel.lunala.project.util.ProfileService
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

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