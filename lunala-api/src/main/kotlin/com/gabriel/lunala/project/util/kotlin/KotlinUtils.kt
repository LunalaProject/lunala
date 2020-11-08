package com.gabriel.lunala.project.util.kotlin

@Retention(AnnotationRetention.BINARY)
@RequiresOptIn(level = RequiresOptIn.Level.WARNING)
@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY, AnnotationTarget.FUNCTION)
annotation class LunalaExperimental

suspend fun <T> valueOrNull(callback: suspend () -> T): T? = runCatching {
    callback()
}.getOrNull()

fun <T> Any.cast(): T = this as T