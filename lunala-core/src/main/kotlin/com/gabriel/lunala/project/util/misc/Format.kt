package com.gabriel.lunala.project.util.misc

fun String.adequatelyFormat(vararg formatting: String): String {
    var result: String = this
    for ((index, format) in formatting.withIndex()) {
        result = result.replace("{$index}", format)
    }
    return result
}