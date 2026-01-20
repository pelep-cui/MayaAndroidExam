package com.rpc.mayaandroidexam.core.retrofit

import com.rpc.mayaandroidexam.core.retrofit.request.SendMoneyRequest
import com.rpc.mayaandroidexam.core.retrofit.request.SignInRequest
import com.rpc.mayaandroidexam.data.remote.TransactionDto
import com.rpc.mayaandroidexam.data.remote.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RetrofitAPIService {

    companion object {
        const val BASE_URL = "https://my-json-server.typicode.com/pelep-cui/fake-service/"
    }

    @POST("users")
    @Headers(value = ["Content-type: application/json; charset=UTF-8"])
    suspend fun signIn(@Body signInRequest: SignInRequest): Response<UserDto>

    @POST("transaction-requests")
    @Headers(value = ["Content-type: application/json; charset=UTF-8"])
    suspend fun sendMoney(@Body sendMoneyRequest: SendMoneyRequest): Response<TransactionDto>
}