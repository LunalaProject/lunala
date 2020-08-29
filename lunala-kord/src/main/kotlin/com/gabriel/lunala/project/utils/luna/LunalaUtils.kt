package com.gabriel.lunala.project.utils.luna

import com.gabriel.lunala.project.Lunala
import com.gabriel.lunala.project.LunalaKord
import com.gabriel.lunala.project.database.LunalaDatabase
import com.gabriel.lunala.project.database.LunalaKordDatabase
import com.gabriel.lunala.project.defaults.ProfileDataFactory
import com.gabriel.lunala.project.entity.LunalaProfile
import com.gabriel.lunala.project.entity.Profile
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.get
import kotlin.coroutines.CoroutineContext

fun Lunala.getProfile(snowflake: Long): Profile? = transaction {
    LunalaProfile.findById(snowflake)
}

fun Lunala.getProfileOrCreate(snowflake: Long) = transaction {
    LunalaProfile.findById(snowflake) ?: LunalaProfile.new(snowflake) {
        ProfileDataFactory.apply(this)
    }
}

suspend fun Lunala.createTransaction(
        context: CoroutineDispatcher = Dispatchers.Main,
        callback: suspend Transaction.() -> Unit
) = newSuspendedTransaction(context = context, db = (Lunala.get<LunalaDatabase>() as LunalaKordDatabase).exposed, statement = callback)