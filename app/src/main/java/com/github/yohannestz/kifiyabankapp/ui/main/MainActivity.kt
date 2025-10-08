package com.github.yohannestz.kifiyabankapp.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.github.yohannestz.kifiyabankapp.data.model.User
import com.github.yohannestz.kifiyabankapp.ui.base.BottomDestination
import com.github.yohannestz.kifiyabankapp.ui.base.BottomDestination.Companion.isBottomDestination
import com.github.yohannestz.kifiyabankapp.ui.base.BottomDestination.Companion.toBottomDestinationIndex
import com.github.yohannestz.kifiyabankapp.ui.base.StartTab
import com.github.yohannestz.kifiyabankapp.ui.base.navigation.NavActionManager
import com.github.yohannestz.kifiyabankapp.ui.base.navigation.NavActionManager.Companion.rememberNavActionManager
import com.github.yohannestz.kifiyabankapp.ui.base.navigation.Route
import com.github.yohannestz.kifiyabankapp.ui.base.navigation.hasDarkStatusBar
import com.github.yohannestz.kifiyabankapp.ui.base.navigation.toRoute
import com.github.yohannestz.kifiyabankapp.ui.base.providers.ProvideCurrentUser
import com.github.yohannestz.kifiyabankapp.ui.base.snackbar.GlobalSnackBarHost
import com.github.yohannestz.kifiyabankapp.ui.main.composable.MainBottomNavBar
import com.github.yohannestz.kifiyabankapp.ui.main.composable.MainNavigationRail
import com.github.yohannestz.kifiyabankapp.ui.theme.KifiyaBankAppTheme
import com.github.yohannestz.kifiyabankapp.ui.theme.dark_scrim
import com.github.yohannestz.kifiyabankapp.ui.theme.light_scrim
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.annotation.KoinExperimentalAPI


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class, KoinExperimentalAPI::class)
class MainActivity : ComponentActivity() {

    val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        splashScreen.setKeepOnScreenCondition {
            viewModel.uiState.value.isLoading
        }

        val lastTabOpened = findLastTabOpened()

        setContent {
            KoinAndroidContext {
                val isDark =
                    false //if (theme == ThemeStyle.FOLLOW_SYSTEM) isSystemInDarkTheme() else theme == ThemeStyle.DARK
                val navController = rememberNavController()
                val navActionManager = rememberNavActionManager(navController)
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val hasAnAccount by viewModel.hasAnAccount.collectAsStateWithLifecycle()

                val windowSizeClass = calculateWindowSizeClass(this)
                val windowWidthSizeClassType = windowSizeClass.widthSizeClass

                ProvideCurrentUser(
                    user = uiState.currentUserProfile
                ) {
                    KifiyaBankAppTheme(
                        darkTheme = isDark,
                        dynamicColor = false
                    ) {
                        val backgroundColor = MaterialTheme.colorScheme.background

                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = backgroundColor
                        ) {
                            MainView(
                                windowWidthSizeClass = windowWidthSizeClassType,
                                navController = navController,
                                navActionManager = navActionManager,
                                lastTabOpened = lastTabOpened,
                                saveLastTab = viewModel::saveLastTab,
                                currentUserProfile = uiState.currentUserProfile,
                                hasAnAccount = hasAnAccount,
                                isLoading = uiState.isLoading,
                            )

                            DisposableEffect(navBackStackEntry) {
                                val route = navBackStackEntry?.destination?.route?.toRoute()
                                val darkStatusBar = route?.hasDarkStatusBar() ?: false

                                val statusBarStyle = if (darkStatusBar) {
                                    SystemBarStyle.dark(android.graphics.Color.TRANSPARENT)
                                } else {
                                    SystemBarStyle.light(
                                        android.graphics.Color.TRANSPARENT,
                                        light_scrim.toArgb()
                                    )
                                }

                                enableEdgeToEdge(
                                    statusBarStyle = statusBarStyle,
                                    navigationBarStyle = SystemBarStyle.auto(
                                        light_scrim.toArgb(),
                                        dark_scrim.toArgb()
                                    ) { darkStatusBar },
                                )

                                onDispose {}
                            }
                        }
                    }
                }
            }

        }
    }

    private fun findLastTabOpened(): Int {
        var lastTabOpened =
            intent.action?.toBottomDestinationIndex()
                ?: StartTab.HOME.value.toBottomDestinationIndex()

        if (lastTabOpened == null) {
            lastTabOpened = runBlocking { viewModel.lastTab.first() }
        } else {
            viewModel.saveLastTab(lastTabOpened)
        }
        return lastTabOpened
    }
}

@Composable
fun MainView(
    windowWidthSizeClass: WindowWidthSizeClass,
    navController: NavHostController,
    navActionManager: NavActionManager,
    lastTabOpened: Int,
    saveLastTab: (Int) -> Unit,
    currentUserProfile: User?,
    hasAnAccount: Boolean,
    isLoading: Boolean,
) {
    val density = LocalDensity.current

    val bottomBarState = remember { mutableStateOf(true) }
    var topBarHeightPx by remember { mutableFloatStateOf(0f) }
    val topBarOffsetY = remember { Animatable(0f) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val isBottomDestination by remember {
        derivedStateOf { navBackStackEntry?.isBottomDestination() == true }
    }

    Scaffold(
        snackbarHost = {
            GlobalSnackBarHost()
        },
        bottomBar = {
            if (windowWidthSizeClass == WindowWidthSizeClass.Compact) {
                MainBottomNavBar(
                    navController = navController,
                    navBackStackEntry = navBackStackEntry,
                    isVisible = isBottomDestination && bottomBarState.value,
                    onItemSelected = saveLastTab,
                    topBarOffsetY = topBarOffsetY
                )
            }
        }
    ) { padding ->
        if (isLoading) {
            LoadingView(
                paddingValues = padding
            )
        } else {
            val startDestination = /*if (currentUserProfile != null || hasAnAccount) {
                BottomDestination.values
                    .getOrElse(lastTabOpened) { BottomDestination.Home }.route as Route
            } else {*/
                Route.Login
            //}

            when (windowWidthSizeClass) {
                WindowWidthSizeClass.Medium -> {
                    Row(
                        modifier = Modifier
                            .statusBarsPadding()
                            .padding(padding)
                    ) {
                        MainNavigationRail(
                            navController = navController,
                            onItemSelected = saveLastTab,
                            modifier = Modifier.padding(padding)
                        )
                        MainNavigation(
                            navController = navController,
                            navActionManager = navActionManager,
                            isCompactScreen = false,
                            modifier = Modifier,
                            padding = padding,
                            startDestination = startDestination,
                            topBarHeightPx = topBarHeightPx,
                            topBarOffsetY = topBarOffsetY
                        )
                    }
                }

                WindowWidthSizeClass.Compact -> {
                    LaunchedEffect(padding) {
                        topBarHeightPx = with(density) { padding.calculateTopPadding().toPx() }
                    }

                    MainNavigation(
                        navController = navController,
                        navActionManager = navActionManager,
                        isCompactScreen = true,
                        modifier = Modifier.padding(
                            start = padding.calculateStartPadding(LocalLayoutDirection.current),
                            end = padding.calculateEndPadding(LocalLayoutDirection.current),
                        ),
                        padding = padding,
                        startDestination = startDestination,
                        topBarHeightPx = topBarHeightPx,
                        topBarOffsetY = topBarOffsetY,
                    )
                }

                else -> {
                    Row(
                        modifier = Modifier
                            .statusBarsPadding()
                            .padding(padding)
                    ) {
                        MainNavigationRail(
                            navController = navController,
                            onItemSelected = saveLastTab,
                            navRailExpanded = true,
                            modifier = Modifier.padding(padding)
                        )
                        MainNavigation(
                            navController = navController,
                            navActionManager = navActionManager,
                            isCompactScreen = false,
                            modifier = Modifier,
                            padding = padding,
                            startDestination = startDestination,
                            topBarHeightPx = topBarHeightPx,
                            topBarOffsetY = topBarOffsetY
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingView(paddingValues: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}