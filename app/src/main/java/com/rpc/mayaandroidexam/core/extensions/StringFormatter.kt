package com.rpc.mayaandroidexam.core.extensions

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object StringFormatter {

    fun Double.toAmountFormat(): String {
        return String.format(locale = Locale.US, "%.2f", this)
    }

    fun OffsetDateTime?.toDisplayFormat(): String {
        return if (this == null) {
            "-- --"
        } else {
            val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.US)
            this.format(formatter)
        }
    }
}