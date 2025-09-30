package com.github.yohannestz.kifiyabankapp.data.dto

import com.github.yohannestz.kifiyabankapp.data.dto.transaction.TransactionResponse
import kotlinx.serialization.Serializable

@Serializable
data class Pageable(
    val page: Int = 0,
    val size: Int = 20,
    val sort: List<String> = emptyList()
)

@Serializable
data class SortObject(
    val direction: String? = null,
    val nullHandling: String? = null,
    val ascending: Boolean? = null,
    val property: String? = null,
    val ignoreCase: Boolean? = null
)

@Serializable
data class PageableObject(
    val paged: Boolean? = null,
    val pageNumber: Int? = null,
    val pageSize: Int? = null,
    val offset: Long? = null,
    val sort: List<SortObject>? = null,
    val unpaged: Boolean? = null
)

@Serializable
data class PageTransactionResponse(
    val totalPages: Int,
    val totalElements: Long,
    val pageable: PageableObject? = null,
    val size: Int,
    val content: List<TransactionResponse>,
    val number: Int,
    val sort: List<SortObject>? = null,
    val first: Boolean,
    val last: Boolean,
    val numberOfElements: Int,
    val empty: Boolean
)