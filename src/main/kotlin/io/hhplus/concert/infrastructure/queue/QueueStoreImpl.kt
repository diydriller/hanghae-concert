package io.hhplus.concert.infrastructure.queue

import io.hhplus.concert.domain.queue.QueueStore
import io.hhplus.concert.domain.queue.QueueToken
import org.springframework.stereotype.Component

@Component
class QueueStoreImpl(
    private val queueTokenRepository: QueueTokenRepository
) : QueueStore {
    override fun saveQueueToken(queueToken: QueueToken): QueueToken {
        return queueTokenRepository.save(queueToken)
    }
}