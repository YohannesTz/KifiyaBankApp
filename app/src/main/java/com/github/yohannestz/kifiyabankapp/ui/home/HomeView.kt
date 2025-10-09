package com.github.yohannestz.kifiyabankapp.ui.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.yohannestz.kifiyabankapp.R
import com.github.yohannestz.kifiyabankapp.ui.base.navigation.NavActionManager
import com.github.yohannestz.kifiyabankapp.ui.base.navigation.Route
import com.github.yohannestz.kifiyabankapp.ui.base.providers.LocalCurrentUser
import com.github.yohannestz.kifiyabankapp.ui.base.snackbar.GlobalSnackBarController
import com.github.yohannestz.kifiyabankapp.ui.home.composables.AccountListItem
import com.github.yohannestz.kifiyabankapp.ui.home.composables.HomeHeader
import com.github.yohannestz.kifiyabankapp.ui.home.composables.ServiceItem
import com.github.yohannestz.kifiyabankapp.ui.home.composables.TransactionListItem
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeView(
    navActionManager: NavActionManager,
    padding: PaddingValues,
) {
    val viewModel: HomeViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.navigationCommands.collect { route ->
            navActionManager.navigateTo(route)
        }
    }

    HomeViewContent(
        uiState = uiState,
        event = viewModel,
        padding = padding,
        navActionManager = navActionManager
    )
}

@Composable
private fun HomeViewContent(
    uiState: HomeViewUiState,
    event: HomeViewUiEvent?,
    padding: PaddingValues,
    navActionManager: NavActionManager
) {
    val currentLocalUser = LocalCurrentUser.current
    var showCurrentBalance by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.message) {
        Log.e("MESSAGE", uiState.message.toString())
        uiState.message?.let { message ->
            GlobalSnackBarController.info(message)
            event?.onMessageDisplayed()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        HomeHeader(
            padding = padding,
            userName = currentLocalUser?.username ?: "Guest",
            balance = (uiState.accounts.firstOrNull()?.balance ?: 0.0).toInt(),
            isBalanceVisible = showCurrentBalance,
            onToggleBalance = {
                showCurrentBalance = !showCurrentBalance
            }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = 180.dp)
                .clip(RoundedCornerShape(topStart = 48.dp, topEnd = 48.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 16.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.Top,
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ServiceItem(
                        iconResId = R.drawable.ic_round_sync_alt_24,
                        titleResId = R.string.transfer,
                    ) {
                        navActionManager.navigateTo(Route.Transfer)
                    }

                    ServiceItem(
                        iconResId = R.drawable.ic_outline_article_24,
                        titleResId = R.string.bills,
                    ) { }

                    ServiceItem(
                        iconResId = R.drawable.ic_outline_mobile_24,
                        titleResId = R.string.recharge,
                    ) { }

                    ServiceItem(
                        iconResId = R.drawable.ic_outline_more_horiz_24,
                        titleResId = R.string.more,
                    ) { }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = stringResource(R.string.my_accounts),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = stringResource(R.string.view_all),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                when {
                    uiState.isLoading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    uiState.accounts.isEmpty() -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = stringResource(R.string.no_account_found))
                        }
                    }

                    else -> {
                        AccountListContainer(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            uiState.accounts.forEachIndexed { index, account ->
                                AccountListItem(
                                    iconResId = R.drawable.ic_outline_account_circle_24,
                                    accountType = account.accountType.name,
                                    accountNumber = account.accountNumber,
                                    balance = account.balance.toInt(),
                                    lastUpdated = "01/24",
                                    showDivider = index < uiState.accounts.size - 1
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.recent_transactions),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = stringResource(R.string.view_all),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                when {
                    uiState.isLoadingTransaction -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    uiState.recentTransactions.isEmpty() -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = stringResource(R.string.no_transaction_found))
                        }
                    }

                    else -> {
                        TransactionListContainer(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            uiState.recentTransactions.forEachIndexed { index, transaction ->
                                TransactionListItem(
                                    iconResId = R.drawable.ic_round_sync_alt_24,
                                    title = transaction.accountId.toString(),
                                    spentAmount = transaction.amount.toInt(),
                                    showDivider = index < uiState.recentTransactions.size - 1
                                )
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(64.dp))
            }
        }
    }
}

@Composable
fun AccountListContainer(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(
                1.dp,
                MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
    ) {
        content()
    }
}

@Composable
fun TransactionListContainer(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(
                1.dp,
                MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
    ) {
        content()
    }
}