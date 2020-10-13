@file:Suppress("unused")

package com.gabriel.lunala.project.util

import arrow.fx.IO
import arrow.fx.extensions.fx
import com.gabriel.lunala.project.LunalaService
import com.typesafe.config.ConfigFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.hocon.Hocon
import kotlinx.serialization.hocon.decodeFromConfig
import org.slf4j.Logger
import java.io.File

fun inspect(fileName: String, createIfNotExists: Boolean = true): IO<File> = IO.fx {
    val file = File(fileName)

    if (file.exists().not()) {
        if (createIfNotExists) {
            file.createNewFile()
            file.writeBytes(LunalaService::class.java.getResourceAsStream("/$fileName").readAllBytes())
        }
        throw ConfigInspectionException("File $fileName was required but not found.")
    }

    file
}