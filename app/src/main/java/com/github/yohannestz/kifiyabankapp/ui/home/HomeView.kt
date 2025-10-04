package com.github.yohannestz.kifiyabankapp.ui.home

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.yohannestz.kifiyabankapp.R
import com.github.yohannestz.kifiyabankapp.ui.base.navigation.NavActionManager
import com.github.yohannestz.kifiyabankapp.ui.home.composables.AccountListItem
import com.github.yohannestz.kifiyabankapp.ui.home.composables.HomeHeader
import com.github.yohannestz.kifiyabankapp.ui.home.composables.ServiceItem
import com.github.yohannestz.kifiyabankapp.ui.home.composables.TransactionListItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeView(
    navActionManager: NavActionManager,
    padding: PaddingValues,
) {
    val viewModel: HomeViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.message) {
        uiState.message?.let {
            snackBarHostState.showSnackbar(it)
            event?.onMessageDisplayed()
        }
    }

    val accounts = remember {
        listOf(
            Triple("Saving", "100000001011", 2000),
            Triple("Checking", "100000001012", 5000),
        )
    }

    val transactions = remember {
        listOf(
            Triple("Grocery Store", 1500, "Oct 4, 2025"),
            Triple("Electricity Bill", 1200, "Oct 3, 2025"),
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        HomeHeader(
            padding = padding,
            userName = "Jane Foe",
            balance = 8500,
            isBalanceVisible = false,
            onToggleBalance = {  }
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
                    ) { }

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
                AccountListContainer(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    accounts.forEachIndexed { index, (type, number, balance) ->
                        AccountListItem(
                            iconResId = R.drawable.ic_outline_account_circle_24,
                            accountType = type,
                            accountNumber = number,
                            balance = balance,
                            lastUpdated = "01/24",
                            showDivider = index < accounts.size - 1
                        )
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
                TransactionListContainer(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    transactions.forEachIndexed { index, (title, amount, date) ->
                        TransactionListItem(
                            iconResId = R.drawable.ic_round_sync_alt_24,
                            title = title,
                            spentAmount = amount,
                            showDivider = index < transactions.size - 1
                        )
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
            .border(1.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(12.dp))
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
            .border(1.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
    ) {
        content()
    }
}