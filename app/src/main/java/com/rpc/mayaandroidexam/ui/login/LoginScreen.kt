package com.rpc.mayaandroidexam.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rpc.mayaandroidexam.R
import com.rpc.mayaandroidexam.ui.theme.MayaAppTheme

@Composable
fun LoginScreen(viewModel: LoginScreenViewModel = hiltViewModel()) {
    println("navDebug LoginScreenLoaded")
    var username by rememberSaveable { mutableStateOf("") }
    var pinCode by rememberSaveable { mutableStateOf("") }
    var pinCodeVisible by rememberSaveable { mutableStateOf(false) }

    val loginFailedError by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxHeight()
            .imePadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.primaryContainer, shape = CircleShape),
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = stringResource(id = R.string.content_desc_app_icon)
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(top = 25.dp)
                    .fillMaxWidth(0.8f),
                shape = RoundedCornerShape(8.dp),
                value = username,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                onValueChange = { newValue -> username = newValue },
                isError = loginFailedError,
                maxLines = 1,
                supportingText = { if (loginFailedError) { Text(stringResource(R.string.error_invalid_password)) } },
                label = { Text(text = stringResource(R.string.label_common_username).toUpperCase(Locale.current)) }
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                shape = RoundedCornerShape(8.dp),
                value = pinCode,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                onValueChange = { newValue -> pinCode = newValue },
                isError = loginFailedError,
                maxLines = 1,
                supportingText = { if (loginFailedError) { Text(stringResource(R.string.error_invalid_password)) } },
                label = { Text(text = stringResource(R.string.label_common_password).toUpperCase(Locale.current)) },
                trailingIcon = @Composable {
                    val image = if (pinCodeVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    val description = if (pinCodeVisible)
                        stringResource(R.string.content_desc_hide_password)
                    else
                        stringResource(R.string.content_desc_show_password)
                    IconButton(onClick = { pinCodeVisible = !pinCodeVisible }) {
                        Icon(image, contentDescription = description)
                    }
                }
            )

            FilledTonalButton(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .height(56.dp)
                    .fillMaxWidth(0.8f),
                shape = RoundedCornerShape(8.dp),
                onClick = { }
            ) {
                Text(stringResource(R.string.button_label_signin).toUpperCase(Locale.current))
            }
        }
    }
}

@PreviewLightDark
@Composable
fun LoginScreenPreview() {
    MayaAppTheme {
        LoginScreen()
    }
}