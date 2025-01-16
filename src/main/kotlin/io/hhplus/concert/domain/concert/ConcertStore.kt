package io.hhplus.concert.domain.concert

interface ConcertStore {
    fun saveSeat(seat: Seat): Seat

    fun saveConcertSchedule(concertSchedule: ConcertSchedule): ConcertSchedule
}