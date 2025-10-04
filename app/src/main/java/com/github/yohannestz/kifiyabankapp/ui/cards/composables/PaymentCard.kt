package com.github.yohannestz.kifiyabankapp.ui.cards.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.github.yohannestz.kifiyabankapp.R

@Composable
fun PaymentCard(
    cardNumber: String,
    availableBalance: String,
    expiry: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(160.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF2F41AD),
                        Color(0xFFA8B3DC),
                    )
                )
            )
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_visa_logo),
                    contentDescription = null
                )
            }

            Text(
                text = cardNumber,
                style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "Available Balance",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color.White.copy(
                                alpha = 0.8f
                            )
                        )
                    )
                    Text(
                        text = availableBalance,
                        style = MaterialTheme.typography.titleLarge.copy(color = Color.White)
                    )
                }
                Text(
                    text = expiry,
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.White),
                )
            }
        }
    }
}