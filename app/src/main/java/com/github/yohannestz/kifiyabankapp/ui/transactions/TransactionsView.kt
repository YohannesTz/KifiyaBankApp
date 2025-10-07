package com.github.yohannestz.kifiyabankapp.ui.transactions

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.github.yohannestz.kifiyabankapp.R
import com.github.yohannestz.kifiyabankapp.ui.base.navigation.NavActionManager
import com.github.yohannestz.kifiyabankapp.ui.home.TransactionListContainer
import com.github.yohannestz.kifiyabankapp.ui.home.composables.TransactionListItem
import com.github.yohannestz.kifiyabankapp.ui.transactions.composables.TransactionFilterButton

@Composable
fun TransactionsView(
    isCompactScreen: Boolean,
    navActionManager: NavActionManager,
    topBarHeightPx: Float,
    topBarOffsetY: Animatable<Float, AnimationVector1D>,
    padding: PaddingValues,
) {
   TransactionsViewContent(
       isCompactScreen = isCompactScreen,
       navActionManager = navActionManager,
       topBarHeightPx = topBarHeightPx,
       topBarOffsetY = topBarOffsetY,
       padding = padding,
   )
}

@Composable
private fun TransactionsViewContent(
    isCompactScreen: Boolean,
    navActionManager: NavActionManager,
    topBarHeightPx: Float,
    topBarOffsetY: Animatable<Float, AnimationVector1D>,
    padding: PaddingValues,
) {
    val selectedFilter = remember { mutableStateOf("All") }

    val todayTransactions = remember {
        listOf(
            Triple("Grocery", -400, R.drawable.ic_outline_mobile_24),
            Triple("IESCO Bill", -120, R.drawable.ic_outline_article_24)
        )
    }

    val yesterdayTransactions = remember {
        listOf(
            Triple("Fund Transferred", 1200, R.drawable.ic_round_sync_alt_24),
            Triple("Mobile Bill", -235, R.drawable.ic_outline_mobile_24),
            Triple("Salary", 6500, R.drawable.ic_outline_mobile_24),
            Triple("Card Payment", -155, R.drawable.ic_outline_mobile_24)
        )
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
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Text(
                        text = "Transactions",
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            // --- Filter Section ---
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Recent",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "Select Time Range",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TransactionFilterButton(
                        text = "All",
                        selected = selectedFilter.value == "All"
                    ) { selectedFilter.value = "All" }

                    TransactionFilterButton(
                        text = "Income",
                        iconRes = R.drawable.ic_round_sync_alt_24,
                        selected = selectedFilter.value == "Income"
                    ) { selectedFilter.value = "Income" }

                    TransactionFilterButton(
                        text = "Expense",
                        iconRes = R.drawable.ic_round_sync_alt_24,
                        selected = selectedFilter.value == "Expense"
                    ) { selectedFilter.value = "Expense" }
                }
            }

            // --- Today Section ---
            item {
                Text(
                    text = "Today",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))

                TransactionListContainer(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    todayTransactions.forEachIndexed { index, (title, amount, iconRes) ->
                        TransactionListItem(
                            iconResId = iconRes,
                            title = title,
                            spentAmount = amount,
                            showDivider = index < todayTransactions.size - 1
                        )
                    }
                }
            }

            // --- Yesterday Section ---
            item {
                Text(
                    text = "Yesterday",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))

                TransactionListContainer(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    yesterdayTransactions.forEachIndexed { index, (title, amount, iconRes) ->
                        TransactionListItem(
                            iconResId = iconRes,
                            title = title,
                            spentAmount = amount,
                            showDivider = index < yesterdayTransactions.size - 1
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(64.dp)) }
        }
    }
}
