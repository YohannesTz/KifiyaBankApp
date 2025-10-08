package com.github.yohannestz.kifiyabankapp.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class Pageable(
    val page: Int = 0,
    val size: Int = 20,
    val sort: List<String> = emptyList()
)

@Serializable
data class SortObject(
    val sorted: Boolean = false,
    val empty: Boolean = true,
    val unsorted: Boolean = true
)

@Serializable
data class PageableObject(
    val paged: Boolean = false,
    val pageNumber: Int = 0,
    val pageSize: Int = 20,
    val offset: Long = 0,
    val sort: SortObject = SortObject(),
    val unpaged: Boolean = false
)

@Serializable
data class PageResponse<T>(
    val totalPages: Int = 1,
    val totalElements: Long = 0,
    val pageable: PageableObject = PageableObject(),
    val size: Int = 20,
    val content: List<T> = emptyList(),
    val number: Int = 0,
    val sort: SortObject = SortObject(),
    val first: Boolean = true,
    val last: Boolean = true,
    val numberOfElements: Int = 0,
    val empty: Boolean = false
)

@Serializable
data class SortItem(
    val direction: String? = null,
    val nullHandling: String? = null,
    val ascending: Boolean? = null,
    val property: String? = null,
    val ignoreCase: Boolean? = null
)

@Serializable
data class PageObjectResponse<T>(
    val totalPages: Int = 1,
    val totalElements: Long = 0,
    val pageable: PageableObject = PageableObject(),
    val size: Int = 20,
    val content: List<T> = emptyList(),
    val number: Int = 0,
    val sort: List<SortItem> = emptyList(),
    val first: Boolean = true,
    val last: Boolean = true,
    val numberOfElements: Int = 0,
    val empty: Boolean = false
)