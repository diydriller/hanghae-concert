package io.hhplus.concert.infrastructure.point

import io.hhplus.concert.domain.point.PointReader
import io.hhplus.concert.domain.point.UserPoint
import org.springframework.stereotype.Component

@Component
class PointReaderImpl(
    private val userPointRepository: UserPointRepository
) : PointReader {
    override fun findPoint(userId: String): UserPoint? {
        return userPointRepository.findByUserId(userId)
    }
}