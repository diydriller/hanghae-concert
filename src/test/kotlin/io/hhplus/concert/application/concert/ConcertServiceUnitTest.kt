package io.hhplus.concert.application.concert

import io.hhplus.concert.domain.concert.Concert
import io.hhplus.concert.domain.concert.ConcertReader
import io.hhplus.concert.exception.NotFoundException
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDate

@ExtendWith(MockitoExtension::class)
class ConcertServiceUnitTest {
    @InjectMocks
    private lateinit var concertService: ConcertService

    @Mock
    private lateinit var concertReader: ConcertReader

    @Test
    fun `콘서트 스케줄 조회 함수 호출시 콘서트가 없으면 NotFoundException이 발생한다`() {
        // given
        val concertId = "0JETAVJVH0SJQ"
        val page = 0
        val size = 10

        `when`(concertReader.findConcert(concertId)).then { null }

        // when & then
        assertThrows(NotFoundException::class.java) {
            concertService.getConcertSchedule(concertId, page, size)
        }
    }

    @Test
    fun `콘서트 좌석 조회 함수 호출시 콘서트 스케줄이 없으면 NotFoundException이 발생한다`() {
        // given
        val concertId = "0JETAVJVH0SJQ"
        val scheduleId = "0JETAVJVH0SJQ"
        val date = LocalDate.of(2025, 1, 20)

        val concert = Concert(
            id = concertId,
            name = "검정치마 콘서트"
        )

        `when`(concertReader.findConcert(concertId)).then { concert }
        `when`(concertReader.findConcertSchedule(concertId)).then { null }

        // when & then
        assertThrows(NotFoundException::class.java) {
            concertService.getConcertSeat(concertId, scheduleId, date)
        }
    }
}