package io.hhplus.concert.domain.reservation

interface ReservationStore {
    fun saveReservation(reservation: Reservation): Reservation
}