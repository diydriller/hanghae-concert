package io.hhplus.concert.infrastructure.concert

import io.hhplus.concert.domain.concert.ConcertSchedule
import io.hhplus.concert.domain.concert.Seat
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface SeatRepository : JpaRepository<Seat, String> {
    @Query(
        "SELECT s FROM Seat s JOIN s.concertSchedule sc " +
                "WHERE s.concertSchedule = :schedule " +
                "AND FUNCTION('DATE', sc.date) = :date " +
                "AND sc.reservedSeatCount < sc.totalSeatCount "
    )
    fun findReservableSeat(schedule: ConcertSchedule, date: LocalDate): List<Seat>

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Seat s WHERE s.id = :seatId ")
    fun findSeatByIdForUpdate(seatId: String): Seat?

    @Query("SELECT s FROM Seat s WHERE s.id = :seatId ")
    fun findSeatById(seatId: String): Seat?
}