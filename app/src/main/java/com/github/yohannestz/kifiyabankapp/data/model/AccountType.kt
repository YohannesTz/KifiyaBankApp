package com.github.yohannestz.kifiyabankapp.data.model

import com.github.yohannestz.kifiyabankapp.R
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class AccountType(
    val titleResId: Int
) {
    @SerialName("CHECKING")
    CHECKING(R.string.checking),

    @SerialName("SAVINGS")
    SAVINGS(R.string.saving),

    @SerialName("MONEY_MARKET")
    MONEY_MARKET(R.string.money_market),

    @SerialName("INDIVIDUAL_RETIREMENT_ACCOUNT")
    INDIVIDUAL_RETIREMENT_ACCOUNT(R.string.individual_retirement_account),

    @SerialName("FIXED_TIME_DEPOSIT")
    FIXED_TIME_DEPOSIT(R.string.fixed_time_deposit),

    @SerialName("SPECIAL_BLOCKED_ACCOUNT")
    SPECIAL_BLOCKED_ACCOUNT(R.string.special_blocked_account)
}
