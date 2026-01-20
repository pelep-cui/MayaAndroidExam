package com.rpc.mayaandroidexam.core.retrofit.request

import com.google.gson.annotations.Expose
import java.util.UUID
import kotlin.random.Random

data class SignInRequest(
    @Expose val id: Int = 1,
    @Expose val username: String,
    //This is excluded in the serialization since API doesn't have to receive the password
    val password: String,
)