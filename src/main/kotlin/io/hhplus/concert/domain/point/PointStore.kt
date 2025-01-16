package io.hhplus.concert.domain.point

interface PointStore {
    fun savePoint(point: UserPoint): UserPoint
}