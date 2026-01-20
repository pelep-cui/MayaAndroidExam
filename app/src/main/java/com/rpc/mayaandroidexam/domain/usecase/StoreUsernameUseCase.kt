package com.rpc.mayaandroidexam.domain.usecase

import com.rpc.mayaandroidexam.core.MayaAccountManager
import javax.inject.Inject

class StoreUsernameUseCase @Inject constructor(
    private val accountManager: MayaAccountManager,
) {

    fun saveUsername(username: String) {
        accountManager.setLoggedInUser(username)
    }
}