package com.gabriel.lunala.project.utils.extensions

import java.io.File

fun File.fileOrNull(): File? = if (exists()) this else null