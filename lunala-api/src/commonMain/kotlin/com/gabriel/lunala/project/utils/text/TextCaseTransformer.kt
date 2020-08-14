package com.gabriel.lunala.project.utils.text

enum class Case {

    CAMEL,
    SNAKE,
    PASCAL

}

fun String.convert(from: Case, to: Case): String {
    var newStr = this.toLowerCase()

    if (from == Case.PASCAL && to == Case.SNAKE) {
        newStr.forEachIndexed { index, char ->
            if (char == '_') {
                newStr = newStr.replace(newStr[index.plus(1)], newStr[index.plus(1)].toUpperCase())
            }
        }

        newStr = newStr.replace("_", "").capitalize()
    }

    return newStr
}