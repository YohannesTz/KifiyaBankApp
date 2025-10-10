package com.github.yohannestz.kifiyabankapp.ui.transactions

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.yohannestz.kifiyabankapp.R
import com.github.yohannestz.kifiyabankapp.data.model.Transaction
import com.github.yohannestz.kifiyabankapp.data.model.TransactionType
import com.github.yohannestz.kifiyabankapp.ui.base.navigation.NavActionManager
import com.github.yohannestz.kifiyabankapp.ui.base.snackbar.GlobalSnackBarController
import com.github.yohannestz.kifiyabankapp.ui.home.composables.TransactionListItem
import com.github.yohannestz.kifiyabankapp.ui.transactions.composables.TransactionFilterButton
import org.koin.androidx.compose.koinViewModel
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

@Composable
fun TransactionsView(
    navActionManager: NavActionManager,
    padding: PaddingValues,
) {
    val viewModel: TransactionsViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.navigationCommands.collect { route ->
            navActionManager.navigateTo(route)
        }
    }

    TransactionsViewContent(
        padding = padding,
        uiState = uiState,
        event = viewModel
    )
}

@Composable
private fun TransactionsViewContent(
    padding: PaddingValues,
    uiState: TransactionsViewUiState,
    event: TransactionsViewUiEvent?
) {
    val selectedFilter = uiState.selectedFilter
    val context = LocalContext.current

    LaunchedEffect(uiState.message) {
        uiState.message?.let { message ->
            GlobalSnackBarController.info(message)
            event?.onMessageDisplayed()
        }
    }

    val filteredTransactions = uiState.transactions.filter {
        when (selectedFilter) {
            TransactionFilter.ALL -> true
            TransactionFilter.INCOME -> it.amount > 0
            TransactionFilter.EXPENSE -> it.amount < 0
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(padding)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            stickyHeader {
                Column(
                    modifier = Modifier.background(MaterialTheme.colorScheme.background)
                ) {
                    Text(
                        text = stringResource(R.string.title_transactions),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TransactionFilterButton(
                        text = stringResource(R.string.all),
                        selected = selectedFilter == TransactionFilter.ALL
                    ) { event?.onFilterChanged(TransactionFilter.ALL) }

                    TransactionFilterButton(
                        text = stringResource(R.string.income),
                        iconRes = R.drawable.ic_outline_arrow_circle_down_24,
                        tint = Color.Green.copy(alpha = 0.5f),
                        selected = selectedFilter == TransactionFilter.INCOME
                    ) { event?.onFilterChanged(TransactionFilter.INCOME) }

                    TransactionFilterButton(
                        text = stringResource(R.string.expense),
                        iconRes = R.drawable.ic_outline_arrow_circle_up_24,
                        tint = Color.Red.copy(alpha = 0.5f),
                        selected = selectedFilter == TransactionFilter.EXPENSE
                    ) { event?.onFilterChanged(TransactionFilter.EXPENSE) }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            if (uiState.isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            } else if (filteredTransactions.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = stringResource(R.string.no_transaction_found))
                    }
                }
            } else {
                val transactionsByDay = filteredTransactions.groupBy { it.dateString(context) }

                transactionsByDay.forEach { (dayLabel, transactions) ->
                    item {
                        Text(
                            text = dayLabel,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    items(transactions.size) { index ->
                        val transaction = transactions[index]
                        TransactionListItem(
                            iconResId = transaction.iconRes(),
                            title = transaction.displayTitle(),
                            spentAmount = transaction.amount.toInt(),
                            showDivider = index < transactions.size - 1
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(64.dp)) }
        }
    }
}

@Composable
fun Transaction.displayTitle(): String {
    return when (type) {
        TransactionType.FUND_TRANSFER -> stringResource(R.string.transaction_fund_transfer)
        TransactionType.TELLER_TRANSFER -> stringResource(R.string.transaction_teller_transfer)
        TransactionType.ATM_WITHDRAWAL -> stringResource(R.string.transaction_atm_withdrawal)
        TransactionType.TELLER_DEPOSIT -> stringResource(R.string.transaction_teller_deposit)
        TransactionType.BILL_PAYMENT -> description
            ?: stringResource(R.string.transaction_bill_payment)

        TransactionType.ACCESS_FEE -> stringResource(R.string.transaction_access_fee)
        TransactionType.PURCHASE -> description ?: stringResource(R.string.transaction_purchase)
        TransactionType.REFUND -> stringResource(R.string.transaction_refund)
        TransactionType.INTEREST_EARNED -> stringResource(R.string.transaction_interest_earned)
        TransactionType.LOAN_PAYMENT -> stringResource(R.string.transaction_loan_payment)
    }
}

fun Transaction.iconRes(): Int {
    return when (type) {
        TransactionType.FUND_TRANSFER -> R.drawable.ic_round_sync_alt_24
        TransactionType.TELLER_TRANSFER -> R.drawable.ic_round_sync_alt_24
        TransactionType.ATM_WITHDRAWAL -> R.drawable.ic_outline_mobile_24
        TransactionType.TELLER_DEPOSIT -> R.drawable.ic_outline_article_24
        TransactionType.BILL_PAYMENT -> R.drawable.ic_outline_article_24
        TransactionType.ACCESS_FEE -> R.drawable.ic_outline_article_24
        TransactionType.PURCHASE -> R.drawable.ic_outline_mobile_24
        TransactionType.REFUND -> R.drawable.ic_outline_arrow_circle_down_24
        TransactionType.INTEREST_EARNED -> R.drawable.ic_outline_arrow_circle_down_24
        TransactionType.LOAN_PAYMENT -> R.drawable.ic_outline_arrow_circle_up_24
    }
}

fun Transaction.dateString(context: Context): String {
    val today = LocalDate.now()
    val yesterday = today.minusDays(1)
    val txDate = timestamp.toLocalDate()

    return when (txDate) {
        today -> context.getString(R.string.today)
        yesterday -> context.getString(R.string.yesterday)
        else -> txDate.format(DateTimeFormatter.ofPattern("MMM dd"))
    }
}