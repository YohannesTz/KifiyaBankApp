package com.github.yohannestz.kifiyabankapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class AccountType {
    @SerialName("CHECKING")
    CHECKING,

    @SerialName("SAVINGS")
    SAVINGS,

    @SerialName("MONEY_MARKET")
    MONEY_MARKET,

    @SerialName("INDIVIDUAL_RETIREMENT_ACCOUNT")
    INDIVIDUAL_RETIREMENT_ACCOUNT,

    @SerialName("FIXED_TIME_DEPOSIT")
    FIXED_TIME_DEPOSIT,

    @SerialName("SPECIAL_BLOCKED_ACCOUNT")
    SPECIAL_BLOCKED_ACCOUNT
}
