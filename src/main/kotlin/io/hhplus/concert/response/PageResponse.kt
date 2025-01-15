package io.hhplus.concert.response

data class PageResponse<T>(
    val data: List<T>,
    val totalPage: Int,
    val totalElements: Long,
    val page: Int,
    val size: Int
)