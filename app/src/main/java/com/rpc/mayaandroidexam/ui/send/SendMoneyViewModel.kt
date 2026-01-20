package com.rpc.mayaandroidexam.ui.send

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rpc.mayaandroidexam.di.IoDispatcher
import com.rpc.mayaandroidexam.domain.usecase.GetExistingUserUseCase
import com.rpc.mayaandroidexam.domain.usecase.SendMoneyUseCase
import com.rpc.mayaandroidexam.domain.usecase.SignOutUseCase
import com.rpc.mayaandroidexam.ui.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SendMoneyViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getExistingUserUseCase: GetExistingUserUseCase,
    private val sendMoneyUseCase: SendMoneyUseCase,
    private val signOutUseCase: SignOutUseCase,
): ViewModel() {

    private val _navigationRoute = MutableSharedFlow<String>(replay = 1)
    val navigationRoute = _navigationRoute.asSharedFlow()

    private val _state = MutableStateFlow<SendMoneyScreenState>(SendMoneyScreenState.Idle())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(ioDispatcher) {
            getExistingUserUseCase.getExistingUser()
                .collectLatest { user ->
                    if (user == null) {
                        signOut()
                        return@collectLatest
                    }
                    _state.value = SendMoneyScreenState.Idle(user.walletBalance)
                }
        }
    }

    fun onEvent(event: SendMoneyScreenEvent) {
        when (event) {
            is SendMoneyScreenEvent.SendMoney -> sendMoney(event.amount)
            SendMoneyScreenEvent.SignOut -> signOut()
            SendMoneyScreenEvent.DismissBottomSheet -> dismissBottomSheet()
        }
    }

    private fun sendMoney(amount: Double) {
        viewModelScope.launch(ioDispatcher) {
            val currentBalance = state.value.walletBalance
            if (amount > currentBalance) {
                _state.update { currentState -> currentState.insufficientFunds() }
                return@launch
            }
            _state.update { currentState -> currentState.loading() }
            sendMoneyUseCase.sendMoney(amount).collectLatest { result ->
                if (result.isSuccess) {
                    val amount = result.getOrNull() ?: (currentBalance - amount)
                    _state.update { currentState -> currentState.success(amount) }
                } else {
                    _state.update { currentState -> currentState.failed() }
                }
            }
        }
    }

    private fun dismissBottomSheet() {
        viewModelScope.launch(ioDispatcher) {
            _state.update { currentState -> currentState.idle() }
        }
    }

    private fun signOut() {
        viewModelScope.launch(ioDispatcher) {
            signOutUseCase.signOut()
            _navigationRoute.emit(Routes.Auth.Login)
        }
    }
}

sealed class SendMoneyScreenEvent {
    data class SendMoney(val amount: Double): SendMoneyScreenEvent()
    data object SignOut: SendMoneyScreenEvent()
    data object DismissBottomSheet: SendMoneyScreenEvent()
}

sealed class SendMoneyScreenState(
    open val walletBalance: Double = 0.0
) {
    data class Idle(override val walletBalance: Double = 0.0): SendMoneyScreenState(walletBalance)

    data class Loading(override val walletBalance: Double = 0.0): SendMoneyScreenState(walletBalance)
    data class InsufficientFunds(override val walletBalance: Double = 0.0): SendMoneyScreenState(walletBalance)
    data class TransactionSuccessful(override val walletBalance: Double = 0.0): SendMoneyScreenState(walletBalance)
    data class TransactionFailed(override val walletBalance: Double = 0.0): SendMoneyScreenState(walletBalance)

    fun idle(): SendMoneyScreenState {
        return Idle(this.walletBalance)
    }

    fun loading(): SendMoneyScreenState {
        return Loading(this.walletBalance)
    }

    fun insufficientFunds(): SendMoneyScreenState {
        return InsufficientFunds(this.walletBalance)
    }

    fun success(updatedBalance: Double): SendMoneyScreenState {
        return TransactionSuccessful(walletBalance = updatedBalance)
    }

    fun failed(): SendMoneyScreenState {
        return TransactionFailed(walletBalance = this.walletBalance)
    }

    fun showBottomSheet(): Boolean {
        return when (this) {
            is Idle,
            is Loading -> false
            is InsufficientFunds,
            is TransactionSuccessful,
            is TransactionFailed -> true

        }
    }

    fun hasError(): Boolean {
        return when (this) {
            is Idle,
            is Loading,
            is TransactionSuccessful -> false
            is InsufficientFunds,
            is TransactionFailed -> true
        }
    }
}