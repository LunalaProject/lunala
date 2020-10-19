package com.gabriel.lunala.project.entity

import com.gabriel.lunala.project.locale.LocaleWrapper

interface Server {

    val idLong: Long

    fun getLocale(): LocaleWrapper

}