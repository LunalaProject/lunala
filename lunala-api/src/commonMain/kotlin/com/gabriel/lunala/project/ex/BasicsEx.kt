package com.gabriel.lunala.project.ex

inline fun <reified T> T.doAlso(block: (T) -> Unit) {
    this.also(block)
}

inline fun <reified T> T.doApply(block: T.() -> Unit) {
    this.also(block)
}