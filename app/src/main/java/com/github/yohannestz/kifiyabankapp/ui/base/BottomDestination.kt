package com.github.yohannestz.kifiyabankapp.ui.base

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.github.yohannestz.kifiyabankapp.R
import com.github.yohannestz.kifiyabankapp.ui.base.navigation.Route

sealed class BottomDestination(
    val value: String,
    val route: Any,
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
) {

    data object Home : BottomDestination(
        value = "home",
        route = Route.Tab.Home,
        title = R.string.title_home,
        icon = R.drawable.ic_round_home_24
    )

    data object Cards : BottomDestination(
        value = "cards",
        route = Route.Tab.Cards,
        title = R.string.title_cards,
        icon = R.drawable.ic_round_credit_card_24
    )

    data object Transactions : BottomDestination(
        value = "transactions",
        route = Route.Tab.Transactions,
        title = R.string.title_transactions,
        icon = R.drawable.ic_outline_article_24
    )

    data object Profile : BottomDestination(
        value = "profile",
        route = Route.Tab.Profile,
        title = R.string.title_profile,
        icon = R.drawable.ic_round_person_outline_24
    )

    companion object {
        val values = listOf(Home, Cards, Transactions, Profile)
        val railValues = listOf(Home, Cards, Transactions, Profile)
        private val topAppBarDisallowed = listOf(Profile)

        fun String.toBottomDestinationIndex() = when (this) {
            Home.value -> 0
            Cards.value -> 1
            Transactions.value -> 2
            Profile.value -> 3
            else -> null
        }

        fun NavBackStackEntry.isBottomDestination() =
            destination.hierarchy.any { dest ->
                values.any { value -> dest.hasRoute(value.route::class) }
            }

        fun NavBackStackEntry.isTopAppBarDisallowed() =
            destination.hierarchy.any { dest ->
                topAppBarDisallowed.any { value -> dest.hasRoute(value.route::class) }
            }

        @Composable
        fun BottomDestination.Icon(selected: Boolean) {
            val color = if (selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.secondary
            }

            androidx.compose.material3.Icon(
                painter = painterResource(icon),
                contentDescription = stringResource(title),
                tint = color
            )
        }

    }
}
