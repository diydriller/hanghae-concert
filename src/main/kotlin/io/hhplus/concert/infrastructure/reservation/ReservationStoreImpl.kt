package io.hhplus.concert.infrastructure.reservation

import io.hhplus.concert.domain.reservation.Reservation
import io.hhplus.concert.domain.reservation.ReservationStore
import org.springframework.stereotype.Component

@Component
class ReservationStoreImpl(
    private val reservationRepository: ReservationRepository
) : ReservationStore {
    override fun saveReservation(reservation: Reservation): Reservation {
        return reservationRepository.save(reservation)
    }
}