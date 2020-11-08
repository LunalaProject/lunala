@file:Suppress("unused")

package com.gabriel.lunala.project.util

import com.gabriel.lunala.project.Lunala

import java.io.File

fun inspect(fileName: String, createIfNotExists: Boolean = true): File {
    val file = File(fileName)

    if (file.exists().not()) {
        if (createIfNotExists) {
            file.createNewFile()
            file.writeBytes(Lunala::class.java.getResourceAsStream("/$fileName").readAllBytes())
        }
        throw ConfigInspectionException("File $fileName was required but not found.")
    }

    return file
}