package com.rpc.mayaandroidexam.domain.usecase

import com.rpc.mayaandroidexam.core.MayaAccountManager
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val accountManager: MayaAccountManager,
) {

    fun signOut() {
        accountManager.clear()
    }
}