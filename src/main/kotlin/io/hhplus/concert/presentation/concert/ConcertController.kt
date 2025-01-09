package io.hhplus.concert.presentation.concert

import io.hhplus.concert.response.BaseResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequestMapping("/concert")
@RestController
class ConcertController {
    @GetMapping
    fun getConcert(): BaseResponse<List<ConcertResponse.GetConcertInfo>> {
        val response = mutableListOf(
            ConcertResponse.GetConcertInfo(1L, "검정치마 연말 콘서트"),
            ConcertResponse.GetConcertInfo(2L, "10cm 콘서트")
        )
        return BaseResponse(response)
    }

    @GetMapping("/{concertId}/schedule")
    fun scheduleConcert(
        @PathVariable concertId: Long
    ): BaseResponse<List<ConcertResponse.GetConcertScheduleInfo>> {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val response = mutableListOf(
            ConcertResponse.GetConcertScheduleInfo(1L, LocalDateTime.parse("2025-01-10 13:10", formatter)),
            ConcertResponse.GetConcertScheduleInfo(2L, LocalDateTime.parse("2025-01-15 18:00", formatter))
        )
        return BaseResponse(response)
    }

    @GetMapping("/{concertId}/schedules/{scheduleId}/seats")
    fun getConcertSeats(
        @PathVariable concertId: String,
        @PathVariable scheduleId: String
    ): BaseResponse<List<ConcertResponse.GetSeatInfo>> {
        val response = mutableListOf(
            ConcertResponse.GetSeatInfo(1L, 30, 100000),
            ConcertResponse.GetSeatInfo(2L, 100, 140000),
        )
        return BaseResponse(response)
    }
}