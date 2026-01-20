package com.rpc.mayaandroidexam.domain.model

import java.time.OffsetDateTime

data class Transaction(
    val id: String,
    val amount: Double,
    val date: OffsetDateTime? = null
)