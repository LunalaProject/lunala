package com.gabriel.lunala.project.utils.state

class State(var bool: Boolean) {

    constructor(result: Result<*>): this(result.isSuccess)

    val name = if (bool) "success" else "failure"
    val state = if (bool) "was successful" else "failed"

}