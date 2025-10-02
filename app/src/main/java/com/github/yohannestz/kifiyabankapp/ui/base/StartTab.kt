package com.github.yohannestz.kifiyabankapp.ui.base

import com.github.yohannestz.kifiyabankapp.R

enum class StartTab(
    val value: String
) {
    LAST_USED("last_used"),
    HOME("home"),
    CARDS("cards"),
    TRANSACTIONS("transactions"),
    PROFILE("profile");

    val stringRes: Int
        get() = when (this) {
            HOME -> R.string.title_home
            CARDS -> R.string.title_cards
            TRANSACTIONS -> R.string.title_transactions
            PROFILE -> R.string.title_profile
            LAST_USED -> R.string.last_used
        }

    companion object {
        fun valueOf(tabName: String) = entries.find { it.value == tabName }
    }
}
