package com.rpc.mayaandroidexam.core.retrofit.request

import com.google.gson.annotations.Expose

data class SendMoneyRequest(
    @Expose private val id: Int = 2, // Fixed id because fake API does not actually persist api calls
    @Expose private val amount: Double,
)