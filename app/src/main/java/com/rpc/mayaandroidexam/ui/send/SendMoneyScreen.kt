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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.rpc.mayaandroidexam.R
import com.rpc.mayaandroidexam.ui.components.AppHeader
import com.rpc.mayaandroidexam.ui.navigation.Routes
import com.rpc.mayaandroidexam.ui.theme.MayaAppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendMoneyScreen(navigateTo: (String) -> Unit = {}) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxHeight()
            .imePadding()
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AppHeader(
            onBack = { navigateTo(Routes.Main.Home) },
            onSignOut = { }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var amount by rememberSaveable { mutableStateOf("0.00") }
            var showBottomSheet by rememberSaveable { mutableStateOf(false) }
            val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
            val scope = rememberCoroutineScope()

            OutlinedTextField(
                modifier = Modifier
                    .padding(top = 25.dp)
                    .fillMaxWidth(0.8f),
                shape = RoundedCornerShape(8.dp),
                value = amount,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                onValueChange = { newValue -> amount = newValue },
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
                onClick = { showBottomSheet = !showBottomSheet }
            ) {
                Text(stringResource(R.string.button_label_submit).toUpperCase(Locale.current))
            }

            if (showBottomSheet) {
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
                                showBottomSheet = false
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
                        FailedModalContent()
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
fun FailedModalContent() {
    Icon(
        imageVector = Icons.Default.Error,
        contentDescription = stringResource(id = R.string.content_desc_error),
        tint = MaterialTheme.colorScheme.error,
        modifier = Modifier.padding(8.dp)
    )
    Text(stringResource(R.string.content_message_failure),)
}



@PreviewLightDark
@Composable
fun SendMoneyScreenPreview() {
    MayaAppTheme {
        SendMoneyScreen()
    }
}