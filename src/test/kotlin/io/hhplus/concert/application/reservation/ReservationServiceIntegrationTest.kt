package io.hhplus.concert.application.reservation

import io.hhplus.concert.config.BaseIntegrationTest
import io.hhplus.concert.domain.concert.Concert
import io.hhplus.concert.domain.concert.ConcertSchedule
import io.hhplus.concert.domain.concert.Seat
import io.hhplus.concert.domain.queue.QueueToken
import io.hhplus.concert.exception.ConflictException
import io.hhplus.concert.infrastructure.concert.ConcertRepository
import io.hhplus.concert.infrastructure.concert.ConcertScheduleRepository
import io.hhplus.concert.infrastructure.concert.SeatRepository
import io.hhplus.concert.infrastructure.queue.QueueTokenRepository
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import kotlin.test.assertEquals

@SpringBootTest
class ReservationServiceIntegrationTest : BaseIntegrationTest() {
    @Autowired
    private lateinit var concertRepository: ConcertRepository

    @Autowired
    private lateinit var reservationService: ReservationService

    @Autowired
    private lateinit var queueTokenRepository: QueueTokenRepository

    @Autowired
    private lateinit var concertScheduleRepository: ConcertScheduleRepository

    @Autowired
    private lateinit var seatRepository: SeatRepository

    @Test
    fun `예약함수 호출시 Reservation이 저장되고 반환된다`() {
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
        queueTokenRepository.save(queueToken)

        val concert = Concert(
            id = concertId,
            name = "검정치마 콘서트"
        )
        concertRepository.save(concert)

        val concertSchedule = ConcertSchedule(
            id = scheduleId,
            concert = concert,
            date = LocalDateTime.of(2025, 2, 20, 18, 0),
            totalSeatCount = 20
        )
        concertScheduleRepository.save(concertSchedule)

        val seat = Seat(
            id = seatId,
            number = 1,
            price = 12000,
            concertSchedule = concertSchedule
        )
        seatRepository.save(seat)

        // when
        val reservation = reservationService.reserveConcert(
            ReservationCommand(
                userId = userId,
                tokenId = tokenId,
                scheduleId = scheduleId,
                seatId = seatId
            )
        )

        assertEquals(userId, reservation.userId)
        assertEquals(scheduleId, reservation.concertScheduleId)
        assertEquals(seatId, reservation.seatId)
    }

    @Test
    fun `활성화되지 않은 토큰으로 예약함수 호출시 ConflictException이 발생한다`() {
        // given
        val tokenId = "0JETAVJVH0SJG"
        val userId = "0JETAVJVH0SJG"
        val scheduleId = "0JETAVJVH0SJG"
        val seatId = "0JETAVJVH0SJG"

        val queueToken = QueueToken(
            id = tokenId,
            userId = userId,
            expiration = LocalDateTime.now().plusMinutes(10)
        )
        queueTokenRepository.save(queueToken)

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
    fun `같은 예약정보로 예약함수 2번 호출시 ConflictException이 발생한다`() {
        // given
        val tokenId = "0JETAVJVH0SJK"
        val userId = "0JETAVJVH0SJK"
        val scheduleId = "0JETAVJVH0SJK"
        val seatId = "0JETAVJVH0SJK"
        val concertId = "0JETAVJVH0SJK"

        val queueToken = QueueToken(
            id = tokenId,
            userId = userId,
            expiration = LocalDateTime.now().plusMinutes(10)
        )
        queueToken.status = QueueToken.Status.ACTIVE
        queueTokenRepository.save(queueToken)

        val concert = Concert(
            id = concertId,
            name = "검정치마 콘서트"
        )
        concertRepository.save(concert)

        val concertSchedule = ConcertSchedule(
            id = scheduleId,
            concert = concert,
            date = LocalDateTime.of(2025, 2, 20, 18, 0),
            totalSeatCount = 20
        )
        concertScheduleRepository.save(concertSchedule)

        val seat = Seat(
            id = seatId,
            number = 1,
            price = 12000,
            concertSchedule = concertSchedule
        )
        seatRepository.save(seat)

        // when
        reservationService.reserveConcert(
            ReservationCommand(
                userId = userId,
                tokenId = tokenId,
                scheduleId = scheduleId,
                seatId = seatId
            )
        )

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
}