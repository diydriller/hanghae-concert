package io.hhplus.concert.domain.point

interface PointReader {
    fun findPoint(userId: String): UserPoint?

    fun findPointForUpdate(userId: String): UserPoint?
}