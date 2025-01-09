package io.hhplus.concert.infrastructure.concert

import io.hhplus.concert.domain.concert.Concert
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ConcertRepository : JpaRepository<Concert, String> {
    @Query("SELECT c FROM Concert c WHERE c.id = :concertId ")
    fun findConcertById(concertId: String): Concert?
}