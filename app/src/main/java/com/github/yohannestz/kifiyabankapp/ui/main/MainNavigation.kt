package com.github.yohannestz.kifiyabankapp.ui.main

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.github.yohannestz.kifiyabankapp.ui.base.navigation.NavActionManager
import com.github.yohannestz.kifiyabankapp.ui.base.navigation.Route
import com.github.yohannestz.kifiyabankapp.ui.base.navigation.toRoute
import com.github.yohannestz.kifiyabankapp.ui.cards.CardsView
import com.github.yohannestz.kifiyabankapp.ui.home.HomeView
import com.github.yohannestz.kifiyabankapp.ui.login.LoginView
import com.github.yohannestz.kifiyabankapp.ui.profile.ProfileView
import com.github.yohannestz.kifiyabankapp.ui.register.RegisterView
import com.github.yohannestz.kifiyabankapp.ui.transactions.TransactionsView
import com.github.yohannestz.kifiyabankapp.ui.transfer.TransferView

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
    val tabOrder = listOf(
        Route.Tab.Home,
        Route.Tab.Cards,
        Route.Tab.Transactions,
        Route.Tab.Profile
    )

    fun getSlideDirection(from: Route?, to: Route?, isTab: Boolean): AnimatedContentTransitionScope.SlideDirection {
        return if (isTab) {
            if (tabOrder.indexOf(from) < tabOrder.indexOf(to)) {
                AnimatedContentTransitionScope.SlideDirection.Start
            } else {
                AnimatedContentTransitionScope.SlideDirection.End
            }
        } else {
            AnimatedContentTransitionScope.SlideDirection.Start
        }
    }


    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        enterTransition = {
            val from = initialState.destination.route?.toRoute()
            val to = targetState.destination.route?.toRoute()
            val isTab = to is Route.Tab
            slideIntoContainer(
                getSlideDirection(from, to, isTab),
                spring(stiffness = Spring.StiffnessMediumLow)
            )
        },
        exitTransition = {
            val from = initialState.destination.route?.toRoute()
            val to = targetState.destination.route?.toRoute()
            val isTab = from is Route.Tab
            slideOutOfContainer(
                if (isTab) getSlideDirection(from, to, isTab) else AnimatedContentTransitionScope.SlideDirection.End,
                spring(stiffness = Spring.StiffnessMediumLow)
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
                navActionManager = navActionManager,
                padding = padding
            )
        }

        composable<Route.Tab.Cards> {
            CardsView(
                navActionManager = navActionManager,
                padding = padding
            )
        }

        composable<Route.Tab.Transactions> {
            TransactionsView(
                navActionManager = navActionManager,
                padding = padding
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

        composable<Route.Transfer> {
            TransferView(
                navActionManager = navActionManager,
                paddingValues = padding
            )
        }
    }
}