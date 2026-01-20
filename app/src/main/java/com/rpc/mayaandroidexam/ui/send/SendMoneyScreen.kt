package com.rpc.mayaandroidexam.ui.send

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rpc.mayaandroidexam.R
import com.rpc.mayaandroidexam.core.extensions.StringFormatter.toAmountFormat
import com.rpc.mayaandroidexam.ui.components.AppHeader
import com.rpc.mayaandroidexam.ui.components.LoadingDialog
import com.rpc.mayaandroidexam.ui.navigation.Routes
import com.rpc.mayaandroidexam.ui.theme.MayaAppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendMoneyScreen(viewModel: SendMoneyViewModel = hiltViewModel(), navigateTo: (String) -> Unit = {}) {
    val focusManager = LocalFocusManager.current

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
        AppHeader(
            title = "Balance ${state.walletBalance}",
            onBack = { navigateTo(Routes.Main.Home) },
            onSignOut = { viewModel.onEvent(SendMoneyScreenEvent.SignOut) }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var amount by rememberSaveable { mutableStateOf("0.00") }
            val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
            val scope = rememberCoroutineScope()

            if (state is SendMoneyScreenState.TransactionSuccessful) {
                // Resets amount value after success transaction.
                amount = "0.00"
            }
            OutlinedTextField(
                modifier = Modifier
                    .padding(top = 25.dp)
                    .fillMaxWidth(0.8f),
                shape = RoundedCornerShape(8.dp),
                value = amount,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                onValueChange = { newValue ->
                    if (newValue.isEmpty()) {
                        amount = "0.00"
                    } else {
                        amount = (newValue.toDoubleOrNull() ?: 0.0).toAmountFormat()
                    }
                },
                isError = false,
                maxLines = 1,
                supportingText = { },
                label = { Text(text = stringResource(R.string.label_common_amount)) }
            )

            FilledTonalButton(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .height(56.dp)
                    .fillMaxWidth(0.8f),
                shape = RoundedCornerShape(8.dp),
                onClick = {
                    focusManager.clearFocus(true)
                    viewModel.onEvent(SendMoneyScreenEvent.SendMoney(amount.toDoubleOrNull() ?: 0.0)) }
            ) {
                Text(stringResource(R.string.button_label_submit).toUpperCase(Locale.current))
            }

            if (state is SendMoneyScreenState.Loading) {
                LoadingDialog(message = stringResource(R.string.content_message_sending_money))
            }

            if (state.showBottomSheet()) {
                ModalBottomSheet(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    sheetState = sheetState,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    containerColor = MaterialTheme.colorScheme.surface,
                    scrimColor = MaterialTheme.colorScheme.scrim.copy(alpha = 0.6f),
                    onDismissRequest = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                viewModel.onEvent(SendMoneyScreenEvent.DismissBottomSheet)
                            }
                        }
                    }
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        if (state.hasError()) {
                            FailedModalContent(state)
                        } else {
                            SuccessfulModalContent()
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun SuccessfulModalContent() {
    Icon(
        imageVector = Icons.Default.CheckCircle,
        contentDescription = stringResource(id = R.string.content_desc_check),
        tint = Color.Green,
        modifier = Modifier.padding(8.dp)
    )
    Text(stringResource(R.string.content_message_success),)
}

@Composable
fun FailedModalContent(state: SendMoneyScreenState) {
    val message = when (state) {
        is SendMoneyScreenState.InsufficientFunds -> stringResource(R.string.content_message_insufficient_funds)
        is SendMoneyScreenState.TransactionFailed -> stringResource(R.string.content_message_transaction_failed)
        else -> stringResource(R.string.content_message_failure)
    }
    Icon(
        imageVector = Icons.Default.Error,
        contentDescription = stringResource(id = R.string.content_desc_error),
        tint = MaterialTheme.colorScheme.error,
        modifier = Modifier.padding(8.dp)
    )
    Text(message)
}



@PreviewLightDark
@Composable
fun SendMoneyScreenPreview() {
    MayaAppTheme {
        SendMoneyScreen()
    }
}