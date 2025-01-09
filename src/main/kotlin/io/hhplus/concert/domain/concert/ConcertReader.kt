package io.hhplus.concert.domain.concert

import org.springframework.data.domain.Page
import java.time.LocalDate

interface ConcertReader {
    fun findConcert(concertId: String): Concert?

    fun findReservableConcertSchedule(concert: Concert, page: Int, size: Int): Page<ConcertSchedule>

    fun findConcertSchedule(scheduleId: String): ConcertSchedule?

    fun findReservableSeat(schedule: ConcertSchedule, date: LocalDate): List<Seat>
}