package io.hhplus.concert.infrastructure.concert

import io.hhplus.concert.domain.concert.Concert
import io.hhplus.concert.domain.concert.ConcertSchedule
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ConcertScheduleRepository : JpaRepository<ConcertSchedule, String> {
    @Query(
        "SELECT cs FROM ConcertSchedule cs " +
                "WHERE cs.concert = :concert AND cs.reservedSeatCount < cs.totalSeatCount "
    )
    fun findReservableConcertSchedule(concert: Concert, pageable: Pageable): Page<ConcertSchedule>

    @Query("SELECT cs FROM ConcertSchedule cs WHERE cs.id = :scheduleId ")
    fun findConcertScheduleById(scheduleId: String): ConcertSchedule?
}