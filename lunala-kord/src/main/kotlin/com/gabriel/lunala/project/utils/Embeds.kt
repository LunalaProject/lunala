package com.gabriel.lunala.project.utils

import com.gitlab.kordlib.rest.builder.message.EmbedBuilder

fun embed(block: EmbedBuilder.() -> Unit): EmbedBuilder =
        EmbedBuilder().apply(block)