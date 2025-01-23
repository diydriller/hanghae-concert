package io.hhplus.concert.domain.reservation

import io.hhplus.concert.domain.concert.Concert
import io.hhplus.concert.domain.concert.ConcertSchedule
import io.hhplus.concert.domain.concert.Seat
import io.hhplus.concert.exception.ConflictException
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class ReservationDomainServiceUnitTest {
    @InjectMocks
    private lateinit var reservationDomainService: ReservationDomainService

    @Mock
    private lateinit var reservationReader: ReservationReader

    @Test
    fun `임시 예약시 전에 예약했으면 ConflictException이 발생한다`() {
        // given
        val userId = "0JETAVJVH0SJQ"
        val scheduleId = "0JETAVJVH0SJQ"
        val seatId = "0JETAVJVH0SJQ"
        val concertId = "0JETAVJVH0SJQ"
        val reservationId = "0JETAVJVH0SJQ"
        val price = 12000

        val concert = Concert(
            id = concertId,
            name = "검정치마 콘서트"
        )

        val concertSchedule = ConcertSchedule(
            id = scheduleId,
            concert = concert,
            date = LocalDateTime.of(2025, 2, 20, 18, 0),
            totalSeatCount = 20
        )

        val seat = Seat(
            id = seatId,
            number = 1,
            price = price,
            concertSchedule = concertSchedule
        )

        val reservation = Reservation(
            id = reservationId,
            concertScheduleId = scheduleId,
            seatId = seatId,
            userId = userId,
            price = price
        )
        reservation.status = Reservation.Status.RESERVED

        `when`(reservationReader.findReservation(userId, scheduleId, seatId)).then { reservation }

        // when & then
        assertThrows(ConflictException::class.java) {
            reservationDomainService.reserve(
                seat = seat,
                reservation = reservation,
                userId = userId,
                scheduleId = scheduleId
            )
        }
    }

    @Test
    fun `임시 예약시 좌석이 이미 점유되어있으면 ConflictException이 발생한다`() {
        // given
        val userId = "0JETAVJVH0SJK"
        val scheduleId = "0JETAVJVH0SJK"
        val seatId = "0JETAVJVH0SJK"
        val concertId = "0JETAVJVH0SJK"
        val reservationId = "0JETAVJVH0SJK"
        val price = 12000

        val concert = Concert(
            id = concertId,
            name = "검정치마 콘서트"
        )

        val concertSchedule = ConcertSchedule(
            id = scheduleId,
            concert = concert,
            date = LocalDateTime.of(2025, 2, 20, 18, 0),
            totalSeatCount = 20
        )

        val seat = Seat(
            id = seatId,
            number = 1,
            price = price,
            concertSchedule = concertSchedule
        )
        seat.status = Seat.Status.HOLD
        seat.holdExpiration = LocalDateTime.now().plusMinutes(3)

        val reservation = Reservation(
            id = reservationId,
            concertScheduleId = scheduleId,
            seatId = seatId,
            userId = userId,
            price = price
        )

        // when & then
        assertThrows(ConflictException::class.java) {
            reservationDomainService.reserve(
                seat = seat,
                reservation = reservation,
                userId = userId,
                scheduleId = scheduleId
            )
        }
    }

    @Test
    fun `임시 예약시 이미 예약이 완료된 좌석이면 ConflictException이 발생한다`() {
        // given
        val userId = "0JETAVJVH0SJK"
        val scheduleId = "0JETAVJVH0SJK"
        val seatId = "0JETAVJVH0SJK"
        val concertId = "0JETAVJVH0SJK"
        val reservationId = "0JETAVJVH0SJK"
        val price = 12000

        val concert = Concert(
            id = concertId,
            name = "검정치마 콘서트"
        )

        val concertSchedule = ConcertSchedule(
            id = scheduleId,
            concert = concert,
            date = LocalDateTime.of(2025, 2, 20, 18, 0),
            totalSeatCount = 20
        )

        val seat = Seat(
            id = seatId,
            number = 1,
            price = price,
            concertSchedule = concertSchedule
        )
        seat.status = Seat.Status.RESERVED

        val reservation = Reservation(
            id = reservationId,
            concertScheduleId = scheduleId,
            seatId = seatId,
            userId = userId,
            price = price
        )

        // when & then
        assertThrows(ConflictException::class.java) {
            reservationDomainService.reserve(
                seat = seat,
                reservation = reservation,
                userId = userId,
                scheduleId = scheduleId
            )
        }
    }

    @Test
    fun `예약 확정시 좌석을 점유하고 있지 않으면 ConflictException이 발생한다`(){
        // given
        val userId = "0JETAVJVH0SJK"
        val scheduleId = "0JETAVJVH0SJK"
        val seatId = "0JETAVJVH0SJK"
        val concertId = "0JETAVJVH0SJK"
        val reservationId = "0JETAVJVH0SJK"
        val price = 12000

        val concert = Concert(
            id = concertId,
            name = "검정치마 콘서트"
        )

        val concertSchedule = ConcertSchedule(
            id = scheduleId,
            concert = concert,
            date = LocalDateTime.of(2025, 2, 20, 18, 0),
            totalSeatCount = 20
        )

        val seat = Seat(
            id = seatId,
            number = 1,
            price = price,
            concertSchedule = concertSchedule
        )
        seat.status = Seat.Status.HOLD
        seat.holdExpiration = LocalDateTime.now().minusMinutes(3)

        val reservation = Reservation(
            id = reservationId,
            concertScheduleId = scheduleId,
            seatId = seatId,
            userId = userId,
            price = price
        )

        // when & then
        assertThrows(ConflictException::class.java) {
            reservationDomainService.complete(
                seat = seat,
                reservation = reservation,
                concertSchedule = concertSchedule
            )
        }
    }
}