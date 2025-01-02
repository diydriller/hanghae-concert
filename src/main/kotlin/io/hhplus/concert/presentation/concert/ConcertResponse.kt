package io.hhplus.concert.presentation.concert

import java.time.LocalDateTime

class ConcertResponse {
    data class GetConcertInfo(
        val id: Long,
        val name: String
    )

    data class GetConcertScheduleInfo(
        val id: Long,
        val date: LocalDateTime
    )

    data class GetSeatInfo(
        val id: Long,
        val number: Int,
        val price: Int
    )
}