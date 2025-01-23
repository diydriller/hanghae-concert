package io.hhplus.concert.infrastructure.point

import io.hhplus.concert.domain.point.PointStore
import io.hhplus.concert.domain.point.UserPoint
import org.springframework.stereotype.Component

@Component
class PointStoreImpl(
    private val userPointRepository: UserPointRepository
) : PointStore {
    override fun savePoint(point: UserPoint): UserPoint {
        return userPointRepository.save(point)
    }
}