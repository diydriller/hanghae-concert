package io.hhplus.concert.application.reservation

import io.hhplus.concert.domain.concert.Concert
import io.hhplus.concert.domain.concert.ConcertReader
import io.hhplus.concert.domain.concert.ConcertSchedule
import io.hhplus.concert.domain.concert.Seat
import io.hhplus.concert.domain.queue.QueueReader
import io.hhplus.concert.domain.queue.QueueToken
import io.hhplus.concert.domain.reservation.Reservation
import io.hhplus.concert.domain.reservation.ReservationReader
import io.hhplus.concert.domain.reservation.ReservationStore
import io.hhplus.concert.exception.ConflictException
import io.hhplus.concert.exception.NotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class ReservationServiceUnitTest {
    @InjectMocks
    private lateinit var reservationService: ReservationService

    @Mock
    private lateinit var reservationReader: ReservationReader

    @Mock
    private lateinit var reservationStore: ReservationStore

    @Mock
    private lateinit var concertReader: ConcertReader

    @Mock
    private lateinit var queueReader: QueueReader

    @Test
    fun `예약 함수 호출시 토큰이 존재하지 않으면 NotFoundException이 발생한다`() {
        // given
        val tokenId = "0JETAVJVH0SJQ"
        val userId = "0JETAVJVH0SJQ"
        val scheduleId = "0JETAVJVH0SJQ"
        val seatId = "0JETAVJVH0SJQ"

        `when`(queueReader.getQueueToken(tokenId)).then { null }

        // when & then
        assertThrows(NotFoundException::class.java) {
            reservationService.reserveConcert(
                ReservationCommand(
                    userId = userId,
                    tokenId = tokenId,
                    scheduleId = scheduleId,
                    seatId = seatId
                )
            )
        }
    }

    @Test
    fun `예약 함수 호출시 만료시간이 지난 토큰이면 ConflictException이 발생한다`() {
        // given
        val tokenId = "0JETAVJVH0SJQ"
        val userId = "0JETAVJVH0SJQ"
        val scheduleId = "0JETAVJVH0SJQ"
        val seatId = "0JETAVJVH0SJQ"

        val queueToken = QueueToken(
            id = tokenId,
            userId = userId,
            expiration = LocalDateTime.now().minusMinutes(1)
        )

        `when`(queueReader.getQueueToken(tokenId)).then { queueToken }

        // when & then
        assertThrows(ConflictException::class.java) {
            reservationService.reserveConcert(
                ReservationCommand(
                    userId = userId,
                    tokenId = tokenId,
                    scheduleId = scheduleId,
                    seatId = seatId
                )
            )
        }
    }

    @Test
    fun `예약 함수 호출시 콘서트 스케줄이 존재하지 않으면 NotFoundException이 발생한다`() {
        // given
        val tokenId = "0JETAVJVH0SJQ"
        val userId = "0JETAVJVH0SJQ"
        val scheduleId = "0JETAVJVH0SJQ"
        val seatId = "0JETAVJVH0SJQ"

        val queueToken = QueueToken(
            id = tokenId,
            userId = userId,
            expiration = LocalDateTime.now().plusMinutes(10)
        )
        queueToken.status = QueueToken.Status.ACTIVE

        `when`(queueReader.getQueueToken(tokenId)).then { queueToken }

        `when`(concertReader.findConcertSchedule(scheduleId)).then { null }

        // when & then
        assertThrows(NotFoundException::class.java) {
            reservationService.reserveConcert(
                ReservationCommand(
                    userId = userId,
                    tokenId = tokenId,
                    scheduleId = scheduleId,
                    seatId = seatId
                )
            )
        }
    }

    @Test
    fun `예약 함수 호출시 좌석이 존재하지 않으면 NotFoundException이 발생한다`() {
        // given
        val tokenId = "0JETAVJVH0SJQ"
        val userId = "0JETAVJVH0SJQ"
        val scheduleId = "0JETAVJVH0SJQ"
        val seatId = "0JETAVJVH0SJQ"
        val concertId = "0JETAVJVH0SJQ"

        val queueToken = QueueToken(
            id = tokenId,
            userId = userId,
            expiration = LocalDateTime.now().plusMinutes(10)
        )
        queueToken.status = QueueToken.Status.ACTIVE

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

        `when`(queueReader.getQueueToken(tokenId)).then { queueToken }

        `when`(concertReader.findConcertSchedule(scheduleId)).then { concertSchedule }

        `when`(concertReader.findSeat(seatId)).then { null }

        // when & then
        assertThrows(NotFoundException::class.java) {
            reservationService.reserveConcert(
                ReservationCommand(
                    userId = userId,
                    tokenId = tokenId,
                    scheduleId = scheduleId,
                    seatId = seatId
                )
            )
        }
    }

    @Test
    fun `예약함수 호출시 이미 예약되어있으면 ConflictException이 발생한다`() {
        // given
        val tokenId = "0JETAVJVH0SJQ"
        val userId = "0JETAVJVH0SJQ"
        val scheduleId = "0JETAVJVH0SJQ"
        val seatId = "0JETAVJVH0SJQ"
        val concertId = "0JETAVJVH0SJQ"
        val reservationId = "0JETAVJVH0SJQ"

        val queueToken = QueueToken(
            id = tokenId,
            userId = userId,
            expiration = LocalDateTime.now().plusMinutes(10)
        )
        queueToken.status = QueueToken.Status.ACTIVE

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
            price = 12000,
            concertSchedule = concertSchedule
        )

        val reservation = Reservation(
            id = reservationId,
            concertScheduleId = scheduleId,
            seatId = seatId,
            userId = userId
        )

        `when`(queueReader.getQueueToken(tokenId)).then { queueToken }

        `when`(concertReader.findConcertSchedule(scheduleId)).then { concertSchedule }

        `when`(concertReader.findSeat(seatId)).then { seat }

        `when`(reservationReader.findReservation(userId, scheduleId, seatId)).then { reservation }

        // when & then
        assertThrows(ConflictException::class.java) {
            reservationService.reserveConcert(
                ReservationCommand(
                    userId = userId,
                    tokenId = tokenId,
                    scheduleId = scheduleId,
                    seatId = seatId
                )
            )
        }
    }

    @Test
    fun `예약함수가 정상적으로 호출시 예약 정보가 알맞은 Reservation을 반환한다`() {
        // given
        val tokenId = "0JETAVJVH0SJQ"
        val userId = "0JETAVJVH0SJQ"
        val scheduleId = "0JETAVJVH0SJQ"
        val seatId = "0JETAVJVH0SJQ"
        val concertId = "0JETAVJVH0SJQ"
        val reservationId = "0JETAVJVH0SJQ"

        val queueToken = QueueToken(
            id = tokenId,
            userId = userId,
            expiration = LocalDateTime.now().plusMinutes(10)
        )
        queueToken.status = QueueToken.Status.ACTIVE

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
            price = 12000,
            concertSchedule = concertSchedule
        )

        val reservation = Reservation(
            id = reservationId,
            concertScheduleId = scheduleId,
            seatId = seatId,
            userId = userId
        )

        `when`(queueReader.getQueueToken(tokenId)).then { queueToken }

        `when`(concertReader.findConcertSchedule(scheduleId)).then { concertSchedule }

        `when`(concertReader.findSeat(seatId)).then { seat }

        `when`(reservationReader.findReservation(userId, scheduleId, seatId)).then { null }

        `when`(reservationStore.saveReservation(any())).then { reservation }

        // when & then
        val savedReservation = reservationService.reserveConcert(
            ReservationCommand(
                userId = userId,
                tokenId = tokenId,
                scheduleId = scheduleId,
                seatId = seatId
            )
        )

        assertEquals(userId, savedReservation.userId)
        assertEquals(seatId, savedReservation.seatId)
        assertEquals(scheduleId, savedReservation.concertScheduleId)
    }
}