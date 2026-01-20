package com.rpc.mayaandroidexam.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun LoadingDialog(
    // onDismissRequest is required, but a loading dialog typically shouldn't be dismissible
    // by user action (like tapping outside or pressing back), so we can provide an empty lambda
    // or use DialogProperties to prevent dismissal.
    onDismissRequest: () -> Unit = { /* Do nothing to prevent user dismissal */ },
    // You can add an optional message
    message: String? = null
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = false, // Prevents dismissal on back button press
            dismissOnClickOutside = false // Prevents dismissal on tap outside
        )
    ) {
        // Custom UI for the dialog content
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth(fraction = 0.8f)
                .height(120.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(48.dp)
                )
                if (!message.isNullOrEmpty()) {
                    Text(
                        text = message,
                        modifier = Modifier
                            .padding(top = 16.dp),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}