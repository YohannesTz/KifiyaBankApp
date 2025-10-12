package com.github.yohannestz.kifiyabankapp.ui.cards.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.fontscaling.MathUtils.lerp
import com.github.yohannestz.kifiyabankapp.R
import com.github.yohannestz.kifiyabankapp.data.model.BankAccount
import java.util.Locale
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardsHorizontalPager(
    modifier: Modifier = Modifier,
    accounts: List<BankAccount>,
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { accounts.size }
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .wrapContentHeight()
        ) { page ->
            val account = accounts[page]
            PaymentCard(
                cardNumber = maskAccountNumber(account.accountNumber),
                availableBalance = stringResource(
                    R.string.cards_balance_amount, String.format(
                        Locale.ROOT,
                        "%.2f",
                        account.balance
                    )
                ),
                expiry = "10/25",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
        }

        CustomPagerIndicator(
            pageCount = accounts.size,
            currentPage = pagerState.currentPage,
            currentPageOffsetFraction = pagerState.currentPageOffsetFraction
        )
    }
}

@SuppressLint("RestrictedApi")
@Composable
fun CustomPagerIndicator(
    pageCount: Int,
    currentPage: Int,
    currentPageOffsetFraction: Float,
    modifier: Modifier = Modifier,
    activeColor: Color = MaterialTheme.colorScheme.primary,
    inactiveColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
    dotSize: Dp = 8.dp,
    dotSpacing: Dp = 8.dp
) {
    Row(
        modifier = modifier.padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { index ->
            val progress = (index - currentPage - currentPageOffsetFraction).absoluteValue
                .coerceIn(0f, 1f)

            val color = lerp(activeColor, inactiveColor, progress)
            val scale = lerp(1.4f, 1f, progress)

            Box(
                modifier = Modifier
                    .size(dotSize)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }
                    .clip(CircleShape)
                    .background(color)
            )

            if (index != pageCount - 1) {
                Spacer(Modifier.width(dotSpacing))
            }
        }
    }
}


private fun maskAccountNumber(accountNumber: String): String {
    return if (accountNumber.length >= 4)
        "**** **** **** ${accountNumber.takeLast(4)}"
    else
        accountNumber
}
