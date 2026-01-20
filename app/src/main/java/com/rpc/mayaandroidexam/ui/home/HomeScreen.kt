package com.rpc.mayaandroidexam.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rpc.mayaandroidexam.R
import com.rpc.mayaandroidexam.ui.components.AppHeader
import com.rpc.mayaandroidexam.ui.navigation.Routes
import com.rpc.mayaandroidexam.ui.theme.MayaAppTheme

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel = hiltViewModel(), navigateTo: (String) -> Unit = {}) {

    val navigationRoute by viewModel.navigationRoute.collectAsStateWithLifecycle("")
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(navigationRoute) {
        navigateTo(navigationRoute)
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxHeight()
            .imePadding()
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AppHeader(username = state.username) {
            viewModel.onEvent(HomeScreenEvent.SignOut)
        }

        WalletBalanceCard(state) { event ->
            viewModel.onEvent(event)
        }
        FilledTonalButton(
            onClick = { navigateTo(Routes.Main.History) },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(stringResource(R.string.button_label_view).toUpperCase(Locale.current))
        }
    }
}

@Composable
fun WalletBalanceCard(state: HomeScreenState, onEvent: (HomeScreenEvent) -> Unit) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier,
        ) {
            Text(
                text = "P ${String.format(locale = java.util.Locale.US, "%.2f", state.balance)}",
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Wallet",
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            FilledTonalButton(
                onClick = { onEvent(HomeScreenEvent.SendMoney) },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(stringResource(R.string.button_label_send).toUpperCase(Locale.current))
            }

        }

    }
}
@PreviewLightDark
@Composable
fun HomeScreenPreview() {
    MayaAppTheme {
        HomeScreen()
    }
}
