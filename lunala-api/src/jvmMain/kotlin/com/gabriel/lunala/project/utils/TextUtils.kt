package com.gabriel.lunala.project.utils

import java.text.Normalizer

fun String.stripAccents(): String {
    return Regex("\\p{InCombiningDiacriticalMarks}+").replace(Normalizer.normalize(this, Normalizer.Form.NFD), "")
}