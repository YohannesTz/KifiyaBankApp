package com.github.yohannestz.kifiyabankapp.ui.main

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.github.yohannestz.kifiyabankapp.ui.base.navigation.NavActionManager
import com.github.yohannestz.kifiyabankapp.ui.base.navigation.Route
import com.github.yohannestz.kifiyabankapp.ui.cards.CardsView
import com.github.yohannestz.kifiyabankapp.ui.home.HomeView
import com.github.yohannestz.kifiyabankapp.ui.login.LoginView
import com.github.yohannestz.kifiyabankapp.ui.profile.ProfileView
import com.github.yohannestz.kifiyabankapp.ui.register.RegisterView
import com.github.yohannestz.kifiyabankapp.ui.transactions.TransactionsView

@Composable
fun MainNavigation(
    navController: NavHostController,
    navActionManager: NavActionManager,
    isCompactScreen: Boolean,
    startDestination: Route,
    modifier: Modifier,
    padding: PaddingValues,
    topBarHeightPx: Float,
    topBarOffsetY: Animatable<Float, AnimationVector1D>,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
            )
        }
    ) {
        composable<Route.Tab.Home> {
            HomeView(
                isCompactScreen = isCompactScreen,
                navActionManager = navActionManager,
                padding = padding,
                topBarHeightPx = topBarHeightPx,
                topBarOffsetY = topBarOffsetY
            )
        }

        composable<Route.Tab.Cards> {
            CardsView(
                isCompactScreen = isCompactScreen,
                navActionManager = navActionManager,
                padding = padding,
                topBarHeightPx = topBarHeightPx,
                topBarOffsetY = topBarOffsetY
            )
        }

        composable<Route.Tab.Transactions> {
            TransactionsView(
                isCompactScreen = isCompactScreen,
                navActionManager = navActionManager,
                padding = padding,
                topBarHeightPx = topBarHeightPx,
                topBarOffsetY = topBarOffsetY
            )
        }

        composable<Route.Tab.Profile> {
            ProfileView(
                isCompactScreen = isCompactScreen,
                navActionManager = navActionManager,
                padding = padding,
                topBarHeightPx = topBarHeightPx,
                topBarOffsetY = topBarOffsetY
            )
        }

        composable<Route.Login> {
            LoginView(
                navActionManager = navActionManager
            )
        }

        composable<Route.Register> {
            RegisterView(
                navActionManager = navActionManager
            )
        }
    }
}