package com.gabriel.lunala.project.service

import arrow.fx.IO
import com.gabriel.lunala.project.entity.Profile

interface ProfileService {

    fun findById(id: Long): IO<Profile>

    fun findOrCreateById(id: Long): IO<Profile>

}