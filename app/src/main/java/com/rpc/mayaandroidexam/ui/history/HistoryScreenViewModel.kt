package com.rpc.mayaandroidexam.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rpc.mayaandroidexam.di.IoDispatcher
import com.rpc.mayaandroidexam.domain.model.Transaction
import com.rpc.mayaandroidexam.domain.usecase.GetTransactionsUseCase
import com.rpc.mayaandroidexam.domain.usecase.SignOutUseCase
import com.rpc.mayaandroidexam.ui.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryScreenViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val signOutUseCase: SignOutUseCase,
): ViewModel() {

    private val _navigationRoute = MutableSharedFlow<String>(replay = 1)
    val navigationRoute = _navigationRoute.asSharedFlow()

    private val _state = MutableStateFlow<HistoryScreenState>(HistoryScreenState.Idle())
    val state = _state.asStateFlow()

    init {
        loadTransactions()
    }

    fun onEvent(event: HistoryScreenEvent) {
        when (event) {
            HistoryScreenEvent.SignOut -> signOut()
        }
    }

    private fun loadTransactions() {
        viewModelScope.launch(ioDispatcher) {
            getTransactionsUseCase.getTransactions()
                .collect { result ->
                    if (result.isSuccess) {
                        _state.value = HistoryScreenState.Idle(result.getOrNull() ?: emptyList())
                    }
                }
        }
    }

    private fun signOut() {
        viewModelScope.launch(ioDispatcher) {
            signOutUseCase.signOut()
            _navigationRoute.emit(Routes.Auth.Login)
        }
    }
}

sealed class HistoryScreenEvent {
    data object SignOut: HistoryScreenEvent()
}

sealed class HistoryScreenState(
    open val transactions: List<Transaction> = emptyList()
) {

    data class Idle(override val transactions: List<Transaction> = emptyList()): HistoryScreenState(transactions)

}