package com.rpc.mayaandroidexam.domain.usecase

import com.rpc.mayaandroidexam.core.MayaAccountManager
import com.rpc.mayaandroidexam.core.database.AppDatabase
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val accountManager: MayaAccountManager,
    private val database: AppDatabase,
) {

    fun signOut() {
        accountManager.clear()
        database.clearAllTables()
    }
}