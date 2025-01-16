package io.hhplus.concert.application.payment

import io.hhplus.concert.domain.concert.ConcertReader
import io.hhplus.concert.domain.concert.ConcertStore
import io.hhplus.concert.domain.concert.Seat
import io.hhplus.concert.domain.payment.Payment
import io.hhplus.concert.domain.payment.PaymentStore
import io.hhplus.concert.domain.point.PointReader
import io.hhplus.concert.domain.point.PointStore
import io.hhplus.concert.domain.reservation.Reservation
import io.hhplus.concert.domain.reservation.ReservationReader
import io.hhplus.concert.domain.reservation.ReservationStore
import io.hhplus.concert.exception.ConflictException
import io.hhplus.concert.exception.NotFoundException
import io.hhplus.concert.response.BaseResponseStatus
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class PaymentService(
    private val pointReader: PointReader,
    private val pointStore: PointStore,
    private val reservationReader: ReservationReader,
    private val reservationStore: ReservationStore,
    private val paymentStore: PaymentStore,
    private val concertReader: ConcertReader,
    private val concertStore: ConcertStore
) {
    @Transactional
    fun pay(command: PaymentCommand) {
        val userPoint = pointReader.findPoint(command.userId)
            ?: throw NotFoundException(BaseResponseStatus.NOT_FOUND_POINT)
        val reservation = reservationReader.findReservation(command.reservationId)
            ?: throw NotFoundException(BaseResponseStatus.NOT_FOUND_RESERVATION)
        val seat = concertReader.findSeat(reservation.seatId)
            ?: throw NotFoundException(BaseResponseStatus.NOT_FOUND_SEAT)
        val concertSchedule = concertReader.findConcertSchedule(reservation.concertScheduleId)
            ?: throw NotFoundException(BaseResponseStatus.NOT_FOUND_CONCERT_SCHEDULE)

        if (!seat.isHolding()) {
            seat.expire()
            throw ConflictException(BaseResponseStatus.NOT_HOLDING_SEAT)
        }
        seat.status = Seat.Status.RESERVED

        reservation.status = Reservation.Status.COMPLETED

        concertSchedule.reservedSeatCount++

        userPoint.spend(reservation.price)
        val payment = Payment(
            userId = command.userId,
            reservationId = command.reservationId,
            totalPrice = reservation.price
        )

        concertStore.saveConcertSchedule(concertSchedule)
        concertStore.saveSeat(seat)
        paymentStore.savePayment(payment)
        pointStore.savePoint(userPoint)
        reservationStore.saveReservation(reservation)
    }
}