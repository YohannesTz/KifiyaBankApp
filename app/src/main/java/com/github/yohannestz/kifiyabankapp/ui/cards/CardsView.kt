package com.github.yohannestz.kifiyabankapp.ui.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.yohannestz.kifiyabankapp.R
import com.github.yohannestz.kifiyabankapp.ui.base.navigation.NavActionManager
import com.github.yohannestz.kifiyabankapp.ui.base.snackbar.GlobalSnackBarController
import com.github.yohannestz.kifiyabankapp.ui.cards.composables.PaymentCard
import com.github.yohannestz.kifiyabankapp.ui.cards.composables.PaymentRequestItem
import com.github.yohannestz.kifiyabankapp.ui.cards.composables.SettingsItem
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@Composable
fun CardsView(
    navActionManager: NavActionManager,
    padding: PaddingValues,
) {
    val viewModel: CardsViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.navigationCommands.collect { route ->
            navActionManager.navigateTo(route)
        }
    }

    CardsViewContent(
        padding = padding,
        uiState = uiState,
        event = viewModel
    )
}

@Composable
private fun CardsViewContent(
    uiState: CardsViewUiState,
    event: CardsViewUiEvent?,
    padding: PaddingValues,
) {
    LaunchedEffect(uiState.message) {
        uiState.message?.let { message ->
            GlobalSnackBarController.info(message)
            event?.onMessageDisplayed()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentAlignment = Alignment.Center
    ) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator()
            }

            uiState.accounts.isEmpty() -> {
                Text(
                    text = stringResource(R.string.no_account_found),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.height(12.dp))

                    Text(
                        text = stringResource(R.string.my_accounts),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )

                    Spacer(Modifier.height(16.dp))

                    uiState.accounts.forEach { account ->
                        PaymentCard(
                            cardNumber = maskAccountNumber(account.accountNumber),
                            availableBalance = "$${
                                String.format(
                                    Locale.ROOT,
                                    "%.2f",
                                    account.balance
                                )
                            }",
                            expiry = "",
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(16.dp))
                    }

                    if (uiState.bills.isNotEmpty()) {
                        Text(
                            text = stringResource(R.string.recent_bills),
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )

                        Spacer(Modifier.height(8.dp))

                        uiState.bills.forEach { bill ->
                            PaymentRequestItem(
                                title = bill.biller,
                                due = stringResource(R.string.due_account, bill.accountNumber),
                                amount = stringResource(
                                    R.string.etb_bill_amount,
                                    String.format(Locale.ROOT, "%.2f", bill.amount)
                                ),
                                onClick = { /* future: navigate to details */ }
                            )

                            Spacer(Modifier.height(8.dp))
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = stringResource(R.string.no_pending_bills))
                        }
                    }

                    Spacer(Modifier.height(24.dp))

                    SettingsSection()
                }
            }
        }
    }
}

@Composable
private fun SettingsSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                shape = RoundedCornerShape(16.dp)
            )
            .background(MaterialTheme.colorScheme.surface)
    ) {
        SettingsItem(
            iconRes = R.drawable.ic_outline_article_24,
            title = "View Statement"
        ) { /* Handle click */ }

        HorizontalDivider()

        SettingsItem(
            iconRes = R.drawable.ic_outline_article_24,
            title = "Change Pin"
        ) { /* Handle click */ }

        HorizontalDivider()

        SettingsItem(
            iconRes = R.drawable.ic_outline_article_24,
            title = "Remove Card"
        ) { /* Handle click */ }
    }
}

private fun maskAccountNumber(accountNumber: String): String {
    return if (accountNumber.length >= 4)
        "**** **** **** ${accountNumber.takeLast(4)}"
    else
        accountNumber
}
