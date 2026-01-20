package com.rpc.mayaandroidexam.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rpc.mayaandroidexam.di.IoDispatcher
import com.rpc.mayaandroidexam.domain.usecase.GetExistingUserUseCase
import com.rpc.mayaandroidexam.domain.usecase.SignInUseCase
import com.rpc.mayaandroidexam.domain.usecase.StoreUsernameUseCase
import com.rpc.mayaandroidexam.ui.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getExistingUserUseCase: GetExistingUserUseCase,
    private val signInUseCase: SignInUseCase,
    private val storeUsernameUserCase: StoreUsernameUseCase,
): ViewModel() {

    private val _navigationRoute = MutableSharedFlow<String>(replay = 1)
    val navigationRoute = _navigationRoute.asSharedFlow()
    private val _loginScreenState = MutableStateFlow<LoginScreenState>(LoginScreenState.Idle)
    val screenState = _loginScreenState.asStateFlow()

    init {
        checkForExistingUsers()
    }

    private fun checkForExistingUsers() {
        _loginScreenState.value = LoginScreenState.Initializing
        viewModelScope.launch(ioDispatcher) {
            getExistingUserUseCase.getExistingUser().map { it != null }.collectLatest { hasUser ->
                if (hasUser) {
                    _navigationRoute.emit(Routes.Main.Home)
                } else {
                    _loginScreenState.value = LoginScreenState.Idle
                }
            }
        }
    }

    fun onEvent(event: LoginScreenEvent) {
        when(event) {
            is LoginScreenEvent.SignIn -> requestSignIn(event.username, event.password)
        }
    }

    private fun requestSignIn(username: String, password: String) {
        viewModelScope.launch(ioDispatcher) {
            _loginScreenState.value = LoginScreenState.Loading
            signInUseCase.signIn(username, password)
                .collectLatest { success ->
                    if (success) {
                        storeUsernameUserCase.saveUsername(username)
                        _navigationRoute.emit(Routes.Main.Home)
                        _loginScreenState.value = LoginScreenState.SignInSuccess
                    } else {
                        _loginScreenState.value = LoginScreenState.SignInFailed("Unable to complete request.")
                    }

            }
        }
    }
}

sealed class LoginScreenEvent() {

    data class SignIn(val username: String, val password: String): LoginScreenEvent()
}


sealed class LoginScreenState() {
    data object Idle: LoginScreenState()
    data object Initializing: LoginScreenState()
    data object Loading: LoginScreenState()
    data object SignInSuccess: LoginScreenState()
    data class SignInFailed(val message: String): LoginScreenState()
}