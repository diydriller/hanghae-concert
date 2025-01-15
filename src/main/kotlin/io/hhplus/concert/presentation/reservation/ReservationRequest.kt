package io.hhplus.concert.presentation.reservation

import io.hhplus.concert.application.reservation.ReservationCommand

class ReservationRequest {
    data class ReserveConcert(
        val tokenId: String,
        val scheduleId: String,
        val seatId: String
    ) {
        fun toCommand(userId: String): ReservationCommand {
            return ReservationCommand(
                userId = userId,
                scheduleId = scheduleId,
                seatId = seatId
            )
        }
    }
}