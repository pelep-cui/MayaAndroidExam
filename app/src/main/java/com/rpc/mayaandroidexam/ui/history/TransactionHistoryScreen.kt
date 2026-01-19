package com.rpc.mayaandroidexam.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Money
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.rpc.mayaandroidexam.R
import com.rpc.mayaandroidexam.ui.theme.MayaAppTheme
import java.time.OffsetDateTime

private val lists = listOf(
    Pair("Sent P 100.00", OffsetDateTime.now()),
    Pair("Sent P 200.00", OffsetDateTime.now()),
)
@Composable
fun TransactionHistoryScreen() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxHeight()
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.label_header_transaction_history),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            items(lists) { history ->
                val (details, date) = history
                HistoryItem(details, date)
            }
        }
    }
}

@Composable
fun HistoryItem(details: String, date: OffsetDateTime) {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Money,
            contentDescription = stringResource(id = R.string.content_desc_error),
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .weight(1f),
            text = details,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground

        )
        Text(
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth(),
            text = date.toLocalDate().toString(),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
        )
    }
}

@PreviewLightDark
@Composable
fun TransactionHistoryScreenPreview() {
    MayaAppTheme {
        TransactionHistoryScreen()
    }
}