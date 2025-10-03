package com.github.yohannestz.kifiyabankapp.ui.main.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.github.yohannestz.kifiyabankapp.ui.base.BottomDestination
import com.github.yohannestz.kifiyabankapp.ui.base.BottomDestination.Companion.Icon
import com.github.yohannestz.kifiyabankapp.ui.theme.MutedGray
import com.github.yohannestz.kifiyabankapp.ui.theme.NavyBlue
import kotlinx.coroutines.launch

@Composable
fun MainBottomNavBar(
    navController: NavController,
    navBackStackEntry: NavBackStackEntry?,
    isVisible: Boolean,
    onItemSelected: (Int) -> Unit,
    topBarOffsetY: Animatable<Float, AnimationVector1D>,
) {
    val scope = rememberCoroutineScope()

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it })
    ) {
        NavigationBar(
            modifier = Modifier
                .shadow(
                    elevation = 8.dp
                ),
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
        ) {
            BottomDestination.values.forEachIndexed { index, dest ->
                val isSelected = navBackStackEntry?.destination?.hierarchy?.any {
                    it.hasRoute(dest.route::class)
                } == true
                NavigationBarItem(
                    icon = { dest.Icon(selected = isSelected) },
                    label = { Text(text = stringResource(dest.title)) },
                    selected = isSelected,
                    onClick = {
                        if (!isSelected) {
                            scope.launch {
                                topBarOffsetY.animateTo(0f)
                            }

                            onItemSelected(index)
                            navController.navigate(dest.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = NavyBlue,
                        unselectedIconColor = MutedGray,
                        selectedTextColor = NavyBlue,
                        unselectedTextColor = MutedGray,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}