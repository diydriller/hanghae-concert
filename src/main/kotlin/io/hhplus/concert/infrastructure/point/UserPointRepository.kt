package io.hhplus.concert.infrastructure.point

import io.hhplus.concert.domain.point.UserPoint
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserPointRepository : JpaRepository<UserPoint, String> {
    @Query("SELECT up FROM UserPoint up WHERE up.userId = :userId ")
    fun findByUserId(userId: String): UserPoint?
}