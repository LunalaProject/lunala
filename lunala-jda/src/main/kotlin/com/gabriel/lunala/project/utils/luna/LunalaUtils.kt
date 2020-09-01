package com.gabriel.lunala.project.utils.luna

import com.gabriel.lunala.project.Lunala
import com.gabriel.lunala.project.database.LunalaDatabase
import com.gabriel.lunala.project.database.LunalaDatabaseImpl
import com.gabriel.lunala.project.entity.LunalaProfile
import com.gabriel.lunala.project.entity.LunalaServer
import com.gabriel.lunala.project.entity.Profile
import com.gabriel.lunala.project.entity.Server
import com.gabriel.lunala.project.entity.extra.Planet
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.get

fun Lunala.getProfile(snowflake: Long): Profile? = standardTransaction {
    LunalaProfile.findById(snowflake)
}

fun Lunala.getProfileOrCreate(snowflake: Long) = standardTransaction {
    LunalaProfile.findById(snowflake) ?: LunalaProfile.new(snowflake) {
        money = 0
    }
}

fun Lunala.getServer(snowflake: Long): Server? = standardTransaction {
    LunalaServer.findById(snowflake)
}

fun Lunala.getServerOrCreate(snowflake: Long): Server = standardTransaction {
    LunalaServer.findById(snowflake) ?: LunalaServer.new(snowflake) {

    }
}

fun <T> Lunala.standardTransaction(
        callback: Transaction.() -> T
): T = transaction(db = (Lunala.get<LunalaDatabase>() as LunalaDatabaseImpl).database, statement = callback)

suspend fun <T> Lunala.suspendedTransaction(
        context: CoroutineDispatcher = Dispatchers.Main,
        callback: suspend Transaction.() -> T
): T = newSuspendedTransaction(context = context, db = (Lunala.get<LunalaDatabase>() as LunalaDatabaseImpl).database, statement = callback)

