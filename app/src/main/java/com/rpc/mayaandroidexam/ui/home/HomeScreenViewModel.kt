package com.rpc.mayaandroidexam.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rpc.mayaandroidexam.di.IoDispatcher
import com.rpc.mayaandroidexam.domain.usecase.GetExistingUserUseCase
import com.rpc.mayaandroidexam.domain.usecase.SignOutUseCase
import com.rpc.mayaandroidexam.ui.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getExistingUserUseCase: GetExistingUserUseCase,
    private val signOutUseCase: SignOutUseCase,
): ViewModel() {

    private val _navigationRoute = MutableSharedFlow<String>(replay = 1)
    val navigationRoute = _navigationRoute.asSharedFlow()

    private val _state = MutableStateFlow<HomeScreenState>(HomeScreenState.Idle())
    val state = _state.asStateFlow()

    init {
        loadExistingUser()
    }

    private fun loadExistingUser() {
        viewModelScope.launch(ioDispatcher) {
            getExistingUserUseCase.getExistingUser().collectLatest { user ->
                if (user == null) {
                    signOut()
                    return@collectLatest
                }
                _state.value = HomeScreenState.Idle(username = user.username, balance = user.walletBalance)
            }

        }
    }

    fun onEvent(event: HomeScreenEvent) {
        when(event) {
            HomeScreenEvent.SignOut -> signOut()
            HomeScreenEvent.SendMoney -> sendMoney()
        }
    }

    private fun sendMoney() {
        viewModelScope.launch {
            _navigationRoute.emit(Routes.Main.Send)
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            signOutUseCase.signOut()
            _navigationRoute.emit(Routes.Auth.Login)
        }
    }
}

sealed class HomeScreenEvent {
    data object SignOut: HomeScreenEvent()
    data object SendMoney: HomeScreenEvent()
}

sealed class HomeScreenState(
    open val username: String,
    open val balance: Double = 0.0,
) {
    data class Idle(
        override val username: String = "Guest",
        override val balance: Double = 0.0,
    ): HomeScreenState(username, balance)

}