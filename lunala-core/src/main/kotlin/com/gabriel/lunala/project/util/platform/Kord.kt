package com.gabriel.lunala.project.util.platform

import com.gitlab.kordlib.rest.builder.message.EmbedBuilder

fun embed(block: EmbedBuilder.() -> Unit): EmbedBuilder =
        EmbedBuilder().apply(block)