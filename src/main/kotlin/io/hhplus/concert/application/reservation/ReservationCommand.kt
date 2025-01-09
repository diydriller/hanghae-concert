package io.hhplus.concert.application.reservation

data class ReservationCommand(
    val userId: String,
    val tokenId: String,
    val scheduleId: String,
    val seatId: String
)
