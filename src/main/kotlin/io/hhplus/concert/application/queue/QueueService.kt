package io.hhplus.concert.application.queue

import io.hhplus.concert.domain.queue.QueueReader
import io.hhplus.concert.domain.queue.QueueStore
import io.hhplus.concert.domain.queue.QueueToken
import io.hhplus.concert.domain.user.UserReader
import io.hhplus.concert.exception.NotFoundException
import io.hhplus.concert.response.BaseResponseStatus
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class QueueService(
    private val queueStore: QueueStore,
    private val queueReader: QueueReader,
    private val userReader: UserReader
) {
    fun publishQueueToken(userId: String): QueueToken {
        userReader.findUser(userId) ?: throw NotFoundException(BaseResponseStatus.NOT_FOUND_USER)
        val queueToken = QueueToken(
            userId = userId,
            expiration = LocalDateTime.now().plusMinutes(10)
        )
        return queueStore.saveQueueToken(queueToken)
    }

    fun getQueueToken(tokenId: String): QueueTokenOrderInfo {
        val queueToken = queueReader.getQueueToken(tokenId)
            ?: throw NotFoundException(BaseResponseStatus.NOT_FOUND_TOKEN)
        val order = queueReader.getQueueTokenOrder(tokenId)
        return QueueTokenOrderInfo(
            id = queueToken.id,
            expiration = queueToken.expiration,
            status = queueToken.status,
            order = order
        )
    }
}