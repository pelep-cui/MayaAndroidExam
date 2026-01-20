package com.rpc.mayaandroidexam.domain.usecase

import com.rpc.mayaandroidexam.domain.model.Transaction
import com.rpc.mayaandroidexam.domain.repository.SendMoneyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor(
    private val repository: SendMoneyRepository,
) {

    fun getTransactions(): Flow<Result<List<Transaction>>> {
        return flow {
            val result = repository.getTransactions()
            if (result.isSuccess) {
                val transactions = result.getOrNull() ?: emptyList()
                emit(Result.success(transactions.map { Transaction(it.id, it.amount, it.date) }))
            } else {
                emit(Result.failure(RuntimeException("Unable retrieved transactions")))
            }
        }
    }
}