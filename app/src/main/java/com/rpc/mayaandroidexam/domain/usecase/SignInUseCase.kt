package com.rpc.mayaandroidexam.domain.usecase

import com.rpc.mayaandroidexam.core.AuthenticationManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authenticationManager: AuthenticationManager,
) {

    fun signIn(username: String, password: String): Flow<Boolean> {
        return flow {
            val result = authenticationManager.signIn(username, password)
            if (result.isSuccess) {
                emit(true)
            } else {
                emit(false)
            }
        }
    }
}