package io.hhplus.concert.presentation.reservation

import io.hhplus.concert.domain.reservation.Reservation

class ReservationResponse {
    data class ReserveConcert(
        val id: String
    ) {
        companion object {
            fun fromEntity(reservation: Reservation): ReserveConcert {
                return ReserveConcert(
                    id = reservation.id
                )
            }
        }
    }
}