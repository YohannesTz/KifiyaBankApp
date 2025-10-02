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
}