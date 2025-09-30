package com.github.yohannestz.kifiyabankapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class TransactionDirection {
    @SerialName("DEBIT")
    DEBIT,

    @SerialName("CREDIT")
    CREDIT
}
