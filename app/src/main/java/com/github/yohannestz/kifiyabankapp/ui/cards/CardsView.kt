package com.github.yohannestz.kifiyabankapp.ui.cards

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.github.yohannestz.kifiyabankapp.R
import com.github.yohannestz.kifiyabankapp.ui.base.navigation.NavActionManager
import com.github.yohannestz.kifiyabankapp.ui.cards.composables.PaymentCard
import com.github.yohannestz.kifiyabankapp.ui.cards.composables.PaymentRequestItem
import com.github.yohannestz.kifiyabankapp.ui.cards.composables.SegmentedButton
import com.github.yohannestz.kifiyabankapp.ui.cards.composables.SettingsItem

@Composable
fun CardsView(
    isCompactScreen: Boolean,
    navActionManager: NavActionManager,
    topBarHeightPx: Float,
    topBarOffsetY: Animatable<Float, AnimationVector1D>,
    padding: PaddingValues,
) {
    CardsViewContent(
        isCompactScreen = isCompactScreen,
        topBarHeightPx = topBarHeightPx,
        topBarOffsetY = topBarOffsetY,
        padding = padding,
        navActionManager = navActionManager
    )
}

@Composable
private fun CardsViewContent(
    isCompactScreen: Boolean,
    topBarHeightPx: Float,
    topBarOffsetY: Animatable<Float, AnimationVector1D>,
    padding: PaddingValues,
    navActionManager: NavActionManager
) {
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(12.dp))

            Text(
                text = "My Accounts",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(Modifier.height(16.dp))

            PaymentCard(
                cardNumber = "**** **** **** 3245",
                availableBalance = "$2,200",
                expiry = "01/24",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                repeat(3) { index ->
                    val isSelected = index == 1
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .size(if (isSelected) 8.dp else 6.dp)
                            .clip(CircleShape)
                            .background(
                                if (isSelected) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                            )
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            PaymentRequestItem(
                title = "Make a payment",
                due = "Due Feb 10, 2022",
                amount = "2022",
                onClick = {

                }
            )

            Spacer(Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SegmentedButton(
                    text = "Settings",
                    selected = false,
                    onClick = { },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(12.dp))

                SegmentedButton(
                    text = "Transactions",
                    selected = false,
                    onClick = { },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(24.dp))

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

            Spacer(Modifier.height(24.dp))
        }
    }
}
