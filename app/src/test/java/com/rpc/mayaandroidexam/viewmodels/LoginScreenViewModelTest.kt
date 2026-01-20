package com.rpc.mayaandroidexam.viewmodels

import app.cash.turbine.test
import com.rpc.mayaandroidexam.dispatcher.MainDispatcherRule
import com.rpc.mayaandroidexam.domain.model.User
import com.rpc.mayaandroidexam.domain.usecase.GetExistingUserUseCase
import com.rpc.mayaandroidexam.domain.usecase.SignInUseCase
import com.rpc.mayaandroidexam.domain.usecase.StoreUsernameUseCase
import com.rpc.mayaandroidexam.ui.login.LoginScreenState
import com.rpc.mayaandroidexam.ui.login.LoginScreenViewModel
import com.rpc.mayaandroidexam.ui.navigation.Routes
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginScreenViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @RelaxedMockK
    private lateinit var getExistingUserUseCase: GetExistingUserUseCase
    @RelaxedMockK
    private lateinit var signInUseCase: SignInUseCase
    @RelaxedMockK
    private lateinit var storeUsernameUserCase: StoreUsernameUseCase

    private lateinit var viewModel: LoginScreenViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `Should load login page when there are no existing user`() = runTest {
        every { getExistingUserUseCase.getExistingUser() } returns flowOf(null)
        val viewModel = createViewModelInstance()
        assertEquals(LoginScreenState.Idle, viewModel.screenState.value)
    }

    @Test
    fun `Should redirect user to home when when user already exists`() = runTest {
        val user = User(username = "username", walletBalance = 500.0)
        every { getExistingUserUseCase.getExistingUser() } returns flowOf(user)
        val viewModel = createViewModelInstance()
        viewModel.navigationRoute.test {
            val navigationRoutes = awaitItem()
            assertEquals(Routes.Main.Home, navigationRoutes)
            cancelAndIgnoreRemainingEvents()

        }
        assertEquals(LoginScreenState.Initializing, viewModel.screenState.value)
    }



    @After
    fun tearDown() {
        clearAllMocks()
    }

    fun createViewModelInstance(): LoginScreenViewModel {
        return LoginScreenViewModel(
            ioDispatcher = mainDispatcherRule.testDispatcher,
            getExistingUserUseCase = getExistingUserUseCase,
            signInUseCase = signInUseCase,
            storeUsernameUserCase = storeUsernameUserCase
        )
    }
}