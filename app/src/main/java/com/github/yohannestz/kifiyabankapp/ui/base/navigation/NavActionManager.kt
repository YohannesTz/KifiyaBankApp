package com.github.yohannestz.kifiyabankapp.ui.base.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Immutable
class NavActionManager(
    private val navController: NavHostController
) {
    fun navigateTo(route: Route) {
        navController.navigate(route)
    }

    fun navigateTo(route: Route, launchSingleTop: Boolean = false, inclusive: Boolean = false) {
        navController.navigate(route) {
            this.launchSingleTop = launchSingleTop
            popUpTo(route) {
                this.inclusive = inclusive
            }
        }
    }

    fun goBack() {
        navController.popBackStack()
    }

    companion object {
        @Composable
        fun rememberNavActionManager(
            navController: NavHostController = rememberNavController()
        ) = remember {
            NavActionManager(navController)
        }
    }
}
