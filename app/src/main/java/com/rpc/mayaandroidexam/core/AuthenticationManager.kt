package com.rpc.mayaandroidexam.core

import com.rpc.mayaandroidexam.core.retrofit.RetrofitAPIService
import com.rpc.mayaandroidexam.core.retrofit.request.SignInRequest
import com.rpc.mayaandroidexam.data.remote.UserDto
import dagger.Provides
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface AuthenticationManager {

    suspend fun signIn(username: String, password: String): Result<Boolean>

}

class RetrofitAuthenticationManager @Inject constructor(
    private val service: RetrofitAPIService
): AuthenticationManager {
    override suspend fun signIn(
        username: String,
        password: String
    ): Result<Boolean> {
        try {
            val response = service.signIn(SignInRequest(username = username, password = password))
            return if (response.isSuccessful) {
                val created = response.body()?.username == username
                Result.success(created)
            } else {
                Result.success(false)
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }

    }

}