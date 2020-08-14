package com.gabriel.lunala.project.command.exception

class FailException(val callback: () -> Unit): RuntimeException()