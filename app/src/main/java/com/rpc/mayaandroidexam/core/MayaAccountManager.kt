package com.rpc.mayaandroidexam.core

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import jakarta.inject.Singleton
import androidx.core.content.edit
import com.rpc.mayaandroidexam.domain.model.User


@Singleton
class MayaAccountManager @Inject constructor(
    @ApplicationContext context: Context,
) {
    private val sharedPreferences = context.getSharedPreferences("MayaExamAppSharedPreferences", Context.MODE_PRIVATE)

    fun getLoggedInUser(): User?  {
        val username = sharedPreferences.getString(KEY_USERNAME, null) ?: return null
        val walletBalance = sharedPreferences.getFloat(KEY_WALLET_BALANCE, 0f).toDouble()
        return User(username = username, walletBalance = walletBalance)
    }

    fun setLoggedInUser(username: String) {
        sharedPreferences
            .edit(commit = true) {
                putString(KEY_USERNAME, username)
                putFloat(KEY_WALLET_BALANCE, 500f) // Sets starting amount at 500
            }
    }

    fun getCurrentBalance(): Double {
        return sharedPreferences.getFloat(KEY_WALLET_BALANCE, 0f).toDouble()
    }

    fun clear() {
        sharedPreferences
            .edit(commit = true) {
                clear()
            }
    }

    companion object {
        private const val KEY_USERNAME = "username"
        private const val KEY_WALLET_BALANCE = "wallet_balance"

    }
}