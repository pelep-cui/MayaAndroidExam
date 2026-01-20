package com.rpc.mayaandroidexam.domain.repository

import com.rpc.mayaandroidexam.core.database.AppDatabase
import com.rpc.mayaandroidexam.core.retrofit.RetrofitAPIService
import com.rpc.mayaandroidexam.core.retrofit.request.SendMoneyRequest
import com.rpc.mayaandroidexam.data.local.TransactionEntity
import java.time.OffsetDateTime
import javax.inject.Inject

class SendMoneyRepository @Inject constructor(
    private val apiService: RetrofitAPIService,
    private val database: AppDatabase,
) {

    suspend fun sendMoney(amount: Double): Result<Boolean> {
        try {
           val response = apiService.sendMoney(SendMoneyRequest(amount = amount))
            if (response.isSuccessful) {
                database.transactionDao().insert(TransactionEntity(amount = amount, date = OffsetDateTime.now()))
                return Result.success(true)
            } else {
                return Result.failure(RuntimeException("Transaction was not successful."))
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    fun getTransactions(): Result<List<TransactionEntity>> {
        try {
            val transactions = database.transactionDao().getAll()
            return Result.success(transactions)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}