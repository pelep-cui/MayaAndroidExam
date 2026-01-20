package com.rpc.mayaandroidexam.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rpc.mayaandroidexam.data.local.TransactionEntity

@Dao
interface TransactionDao {

    @Query("SELECT * FROM transactions")
    fun getAll(): List<TransactionEntity>

    @Insert
    fun insert(transaction: TransactionEntity)
}