package com.github.yohannestz.kifiyabankapp.ui.base.navigation

import androidx.compose.runtime.Immutable
import androidx.navigation.NavHostController

@Immutable
class NavActionManager(
    private val navController: NavHostController
) {
    fun navigateTo(route: Route) {
        navController.navigate(route)
    }
}
