package com.rpc.mayaandroidexam.domain.repository

import com.rpc.mayaandroidexam.core.retrofit.RetrofitAPIService
import com.rpc.mayaandroidexam.core.retrofit.request.SendMoneyRequest
import javax.inject.Inject

class SendMoneyRepository @Inject constructor(
    private val apiService: RetrofitAPIService
) {

    suspend fun sendMoney(amount: Double): Result<Boolean> {
        try {
           val response = apiService.sendMoney(SendMoneyRequest(amount = amount))
            if (response.isSuccessful) {
                return Result.success(true)
            } else {
                return Result.failure(RuntimeException("Transaction was not successful."))
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}