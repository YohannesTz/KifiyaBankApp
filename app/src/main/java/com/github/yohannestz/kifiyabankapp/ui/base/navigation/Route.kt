package com.github.yohannestz.kifiyabankapp.ui.base.navigation

import kotlinx.serialization.Serializable

sealed interface Route {

    sealed interface Tab: Route {
        @Serializable
        data object Home: Tab

        @Serializable
        data object Cards: Tab

        @Serializable
        data object Transactions: Tab

        @Serializable
        data object Profile: Tab
    }

    @Serializable
    data object Login: Route

    @Serializable
    data object Register: Route

    @Serializable
    data object TransactionList: Route

    @Serializable
    data object AccountsList: Route

    @Serializable
    data object Transfer: Route
}

fun Route.hasDarkStatusBar(): Boolean {
    return when (this) {
        Route.Login,
        Route.Register,
        is Route.Tab.Home -> true

        Route.TransactionList,
        Route.AccountsList,
        Route.Transfer,
        is Route.Tab.Cards,
        is Route.Tab.Transactions,
        is Route.Tab.Profile -> false
    }
}
fun String.toRoute(): Route? {
    return when (this) {
        Route.Login::class.qualifiedName -> Route.Login
        Route.Register::class.qualifiedName -> Route.Register
        Route.TransactionList::class.qualifiedName -> Route.TransactionList
        Route.AccountsList::class.qualifiedName -> Route.AccountsList
        Route.Tab.Home::class.qualifiedName -> Route.Tab.Home
        Route.Tab.Cards::class.qualifiedName -> Route.Tab.Cards
        Route.Tab.Transactions::class.qualifiedName -> Route.Tab.Transactions
        Route.Tab.Profile::class.qualifiedName -> Route.Tab.Profile
        else -> null
    }
}