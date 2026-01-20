package com.rpc.mayaandroidexam.core.extensions

import java.util.Locale

object StringFormatter {

    fun Double.toAmountFormat(): String {
        return String.format(locale = Locale.US, "%.2f", this)
    }
}