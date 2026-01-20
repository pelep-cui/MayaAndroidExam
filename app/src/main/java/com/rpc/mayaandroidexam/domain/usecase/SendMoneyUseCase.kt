package com.rpc.mayaandroidexam.domain.usecase

import com.rpc.mayaandroidexam.core.MayaAccountManager
import com.rpc.mayaandroidexam.domain.repository.SendMoneyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SendMoneyUseCase @Inject constructor(
    private val repository: SendMoneyRepository,
    private val accountManager: MayaAccountManager,
) {

    fun sendMoney(amount: Double): Flow<Result<Double>> {
        return flow {
            val result = repository.sendMoney(amount)
            if (result.isSuccess) {
                val currentBalance = accountManager.getCurrentBalance()
                val updatedBalance = currentBalance - amount
                accountManager.updateCurrentBalance(updatedBalance)
                emit(Result.success(updatedBalance))
            } else {
                emit(Result.failure(RuntimeException("Send Money failed")))
            }
        }
    }

}