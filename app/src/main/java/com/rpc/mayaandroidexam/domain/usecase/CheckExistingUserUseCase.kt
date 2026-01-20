package com.rpc.mayaandroidexam.domain.usecase

import com.rpc.mayaandroidexam.core.MayaAccountManager
import com.rpc.mayaandroidexam.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetExistingUserUseCase @Inject constructor(
    private val mayaAccountManager: MayaAccountManager
) {

    fun getExistingUser(): Flow<User?> {
        return flow {
            emit(mayaAccountManager.getLoggedInUser())
        }
    }
}