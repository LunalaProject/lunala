package com.gabriel.lunala.project.utils.api

import com.gabriel.lunala.project.emojis.Emote
import com.gabriel.lunala.project.utils.state.State
import java.awt.Color

val State.emote: Emote
    get() = if (bool) Emote.Check else Emote.Warn

val State.color: Color
    get() = if (bool) Color(0, 255, 0) else Color(255, 0, 0)