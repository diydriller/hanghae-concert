package io.hhplus.concert.domain.point

interface PointReader {
    fun findPoint(userId: String): UserPoint?
}