package io.hhplus.concert.infrastructure.concert

import io.hhplus.concert.domain.concert.ConcertSchedule
import io.hhplus.concert.domain.concert.ConcertStore
import io.hhplus.concert.domain.concert.Seat
import org.springframework.stereotype.Component

@Component
class ConcertStoreImpl(
    private val concertScheduleRepository: ConcertScheduleRepository,
    private val seatRepository: SeatRepository
) : ConcertStore {
    override fun saveSeat(seat: Seat): Seat {
        return seatRepository.save(seat)
    }

    override fun saveConcertSchedule(concertSchedule: ConcertSchedule): ConcertSchedule {
        return concertScheduleRepository.save(concertSchedule)
    }
}