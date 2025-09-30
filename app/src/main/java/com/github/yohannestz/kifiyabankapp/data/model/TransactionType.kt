package com.github.yohannestz.kifiyabankapp.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
enum class TransactionType {
    @SerialName("FUND_TRANSFER")
    FUND_TRANSFER,

    @SerialName("TELLER_TRANSFER")
    TELLER_TRANSFER,

    @SerialName("ATM_WITHDRAWAL")
    ATM_WITHDRAWAL,

    @SerialName("TELLER_DEPOSIT")
    TELLER_DEPOSIT,

    @SerialName("BILL_PAYMENT")
    BILL_PAYMENT,

    @SerialName("ACCESS_FEE")
    ACCESS_FEE,

    @SerialName("PURCHASE")
    PURCHASE,

    @SerialName("REFUND")
    REFUND,

    @SerialName("INTEREST_EARNED")
    INTEREST_EARNED,

    @SerialName("LOAN_PAYMENT")
    LOAN_PAYMENT
}