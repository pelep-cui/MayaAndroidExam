package com.rpc.mayaandroidexam.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rpc.mayaandroidexam.data.local.TransactionEntity

@Database(entities = [TransactionEntity::class], version = 1, exportSchema = false)
@TypeConverters(OffsetDateTimeConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun transactionDao(): TransactionDao


    companion object {
        const val DB_NAME = "maya-exam-db"
    }
}