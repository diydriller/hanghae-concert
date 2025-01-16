package io.hhplus.concert.application.payment

import io.hhplus.concert.domain.concert.*
import io.hhplus.concert.domain.payment.PaymentStore
import io.hhplus.concert.domain.point.PointReader
import io.hhplus.concert.domain.point.PointStore
import io.hhplus.concert.domain.point.UserPoint
import io.hhplus.concert.domain.reservation.Reservation
import io.hhplus.concert.domain.reservation.ReservationDomainService
import io.hhplus.concert.domain.reservation.ReservationReader
import io.hhplus.concert.domain.reservation.ReservationStore
import io.hhplus.concert.exception.NotFoundException
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class PaymentServiceUnitTest {
    @InjectMocks
    private lateinit var paymentService: PaymentService

    @Mock
    private lateinit var pointReader: PointReader

    @Mock
    private lateinit var pointStore: PointStore

    @Mock
    private lateinit var reservationReader: ReservationReader

    @Mock
    private lateinit var reservationStore: ReservationStore

    @Mock
    private lateinit var paymentStore: PaymentStore

    @Mock
    private lateinit var concertReader: ConcertReader

    @Mock
    private lateinit var concertStore: ConcertStore

    @Mock
    private lateinit var reservationDomainService: ReservationDomainService

    @Test
    fun `유저의 포인트가 없으면 NotFoundExeption이 발생한다`() {
        // given
        val userId = "0JETAVJVH0SJQ"
        val reservationId = "0JETAVJVH0SJQ"

        `when`(pointReader.findPointForUpdate(userId)).then { null }

        // when & then
        assertThrows(NotFoundException::class.java) {
            paymentService.pay(
                PaymentCommand(
                    userId = userId,
                    reservationId = reservationId
                )
            )
        }
    }

    @Test
    fun `임시 예약 내역이 없으면 NotFoundException이 발생한다`() {
        // given
        val pointId = "0JETAVJVH0SJQ"
        val userId = "0JETAVJVH0SJQ"
        val reservationId = "0JETAVJVH0SJQ"

        val userPoint = UserPoint(
            id = pointId,
            userId = userId
        )
        `when`(pointReader.findPointForUpdate(userId)).then { userPoint }

        `when`(reservationReader.findReservationForUpdate(reservationId)).then { null }

        // when & then
        assertThrows(NotFoundException::class.java) {
            paymentService.pay(
                PaymentCommand(
                    userId = userId,
                    reservationId = reservationId
                )
            )
        }
    }

    @Test
    fun `예약한 좌석이 없으면 NotFoundException이 발생한다`() {
        // given
        val pointId = "0JETAVJVH0SJQ"
        val userId = "0JETAVJVH0SJQ"
        val reservationId = "0JETAVJVH0SJQ"
        val scheduleId = "0JETAVJVH0SJQ"
        val seatId = "0JETAVJVH0SJQ"
        val price = 12000

        val userPoint = UserPoint(
            id = pointId,
            userId = userId
        )

        val reservation = Reservation(
            id = reservationId,
            concertScheduleId = scheduleId,
            seatId = seatId,
            userId = userId,
            price = price
        )

        `when`(pointReader.findPointForUpdate(userId)).then { userPoint }

        `when`(reservationReader.findReservationForUpdate(reservationId)).then { reservation }

        `when`(concertReader.findSeatForUpdate(seatId)).then { null }

        // when & then
        assertThrows(NotFoundException::class.java) {
            paymentService.pay(
                PaymentCommand(
                    userId = userId,
                    reservationId = reservationId
                )
            )
        }
    }

    @Test
    fun `예약한 콘서트 스케줄이 없으면 NotFoundExeption이 발생한다`() {
        // given
        val pointId = "0JETAVJVH0SJQ"
        val userId = "0JETAVJVH0SJQ"
        val reservationId = "0JETAVJVH0SJQ"
        val scheduleId = "0JETAVJVH0SJQ"
        val seatId = "0JETAVJVH0SJQ"
        val price = 12000
        val concertId = "0JETAVJVH0SJQ"

        val concert = Concert(
            id = concertId,
            name = "검정치마 콘서트"
        )

        val userPoint = UserPoint(
            id = pointId,
            userId = userId
        )

        val reservation = Reservation(
            id = reservationId,
            concertScheduleId = scheduleId,
            seatId = seatId,
            userId = userId,
            price = price
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
            price = 12000,
            concertSchedule = concertSchedule
        )

        `when`(pointReader.findPointForUpdate(userId)).then { userPoint }

        `when`(reservationReader.findReservationForUpdate(reservationId)).then { reservation }

        `when`(concertReader.findSeatForUpdate(seatId)).then { seat }

        `when`(concertReader.findConcertScheduleForUpdate(scheduleId)).then { null }

        // when & then
        assertThrows(NotFoundException::class.java) {
            paymentService.pay(
                PaymentCommand(
                    userId = userId,
                    reservationId = reservationId
                )
            )
        }
    }

}