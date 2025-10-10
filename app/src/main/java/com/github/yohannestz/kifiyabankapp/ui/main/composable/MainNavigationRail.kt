package com.github.yohannestz.kifiyabankapp.ui.main.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.github.yohannestz.kifiyabankapp.ui.base.BottomDestination
import com.github.yohannestz.kifiyabankapp.ui.base.BottomDestination.Companion.Icon

@Composable
fun MainNavigationRail(
    modifier: Modifier = Modifier,
    navController: NavController,
    navRailExpanded: Boolean = false,
    onItemSelected: (Int) -> Unit,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val railModifier = if (navRailExpanded) {
        modifier.then(
            Modifier.fillMaxWidth(0.25f)
        )
    } else {
        modifier
    }

    NavigationRail(
        modifier = railModifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Bottom
        ) {
            BottomDestination.railValues.forEachIndexed { index, dest ->
                val isSelected = navBackStackEntry?.destination?.hierarchy?.any {
                    it.hasRoute(dest.route::class)
                } == true
                if (!navRailExpanded) {
                    NavigationRailItem(
                        selected = isSelected,
                        onClick = {
                            onItemSelected(index)
                            navController.navigate(dest.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { dest.Icon(selected = isSelected) },
                        label = { Text(text = stringResource(dest.title), maxLines = 1) }
                    )
                } else {
                    NavigationDrawerItem(
                        selected = isSelected,
                        onClick = {
                            onItemSelected(index)
                            navController.navigate(dest.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { dest.Icon(selected = isSelected) },
                        label = { Text(text = stringResource(dest.title), maxLines = 1) },
                        modifier = Modifier
                            .padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    }
}