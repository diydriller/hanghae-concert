package io.hhplus.concert.presentation.reservation

import java.time.LocalDateTime

class ReservationResponse {
    data class ReserveConcert(
        val id: Long,
        val expiration: LocalDateTime
    )
}