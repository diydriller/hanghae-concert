package io.hhplus.concert.application.reservation

import io.hhplus.concert.domain.concert.ConcertReader
import io.hhplus.concert.domain.reservation.Reservation
import io.hhplus.concert.domain.reservation.ReservationReader
import io.hhplus.concert.domain.reservation.ReservationStore
import io.hhplus.concert.exception.ConflictException
import io.hhplus.concert.exception.NotFoundException
import io.hhplus.concert.response.BaseResponseStatus
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class ReservationService(
    private val reservationReader: ReservationReader,
    private val reservationStore: ReservationStore,
    private val concertReader: ConcertReader,
) {
    @Transactional
    fun reserveConcert(command: ReservationCommand): Reservation {
        val schedule = concertReader.findConcertSchedule(command.scheduleId)
            ?: throw NotFoundException(BaseResponseStatus.NOT_FOUND_CONCERT_SCHEDULE)

        val seat = concertReader.findSeatForUpdate(command.seatId)
            ?: throw NotFoundException(BaseResponseStatus.NOT_FOUND_SEAT)

        reservationReader.findReservation(command.userId, schedule.id, seat.id)?.let { reservation ->
            if (reservation.isReserved()) throw ConflictException(BaseResponseStatus.ALREADY_RESERVED)
        }

        seat.hold()

        val reservation = Reservation(
            concertScheduleId = schedule.id,
            seatId = seat.id,
            userId = command.userId,
            price = seat.price
        )
        reservation.reserve()
        return reservationStore.saveReservation(reservation)
    }
}