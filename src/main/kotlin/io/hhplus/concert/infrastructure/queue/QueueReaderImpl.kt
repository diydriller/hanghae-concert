package io.hhplus.concert.infrastructure.queue

import io.hhplus.concert.domain.queue.QueueReader
import io.hhplus.concert.domain.queue.QueueToken
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component

@Component
class QueueReaderImpl(
    private val queueTokenRepository: QueueTokenRepository
) : QueueReader {
    override fun getQueueToken(tokenId: String): QueueToken? {
        return queueTokenRepository.findQueueTokenById(tokenId)
    }

    override fun getQueueTokenOrder(tokenId: String): Int {
        return queueTokenRepository.getQueueTokenOrder(tokenId)
    }

    override fun getInActiveQueueTokenList(num: Int): List<QueueToken> {
        val pageable = PageRequest.of(0, num, Sort.by("id").ascending())
        return queueTokenRepository.getInActiveQueueTokenList(pageable)
    }
}