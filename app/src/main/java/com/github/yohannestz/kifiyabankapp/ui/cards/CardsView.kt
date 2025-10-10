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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.github.yohannestz.kifiyabankapp.ui.cards.composables.AccountCreationSheet
import com.github.yohannestz.kifiyabankapp.ui.cards.composables.CardsHorizontalPager
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

@OptIn(ExperimentalMaterial3Api::class)
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

    LaunchedEffect(Unit) {
        event?.loadAccounts()
    }

    if (uiState.showAccountCreationDialog) {
        AccountCreationSheet(
            uiState = uiState,
            event = event
        ) { event?.showAccountCreationDialog(false) }
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

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(R.string.my_accounts),
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )

                        Spacer(
                            modifier = Modifier.width(8.dp)
                        )
                    }

                    Spacer(Modifier.height(16.dp))

                    CardsHorizontalPager(
                        accounts = uiState.accounts,
                        modifier = Modifier.fillMaxWidth()
                    )

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

                    SettingsSection(
                        onViewStatementClick = {

                        },
                        onChangePinClick = {

                        },
                        onRemoveCardClick = {

                        },
                        onAddAccountClick = {
                            event?.showAccountCreationDialog(true)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingsSection(
    onViewStatementClick: () -> Unit = {},
    onChangePinClick: () -> Unit = {},
    onRemoveCardClick: () -> Unit = {},
    onAddAccountClick: () -> Unit = {}
) {
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
            title = stringResource(R.string.view_statement),
            onClick = onViewStatementClick
        )

        HorizontalDivider()

        SettingsItem(
            iconRes = R.drawable.ic_round_pin_24,
            title = stringResource(R.string.change_pin),
            onClick = onChangePinClick
        )

        HorizontalDivider()

        SettingsItem(
            iconRes = R.drawable.ic_round_credit_card_off_24,
            title = stringResource(R.string.remove_card),
            onClick = onRemoveCardClick
        )

        HorizontalDivider()

        SettingsItem(
            iconRes = R.drawable.ic_round_add_24,
            title = stringResource(R.string.add_account),
            onClick = onAddAccountClick
        )
    }
}
