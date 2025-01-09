package io.hhplus.concert.presentation.reservation

class ReservationRequest {
    data class ReserveConcert(
        val id: Long,
        val scheduleId: Long,
        val seatId: Long
    )
}