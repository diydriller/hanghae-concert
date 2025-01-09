package io.hhplus.concert.presentation.queue

import io.hhplus.concert.application.queue.QueueTokenOrderInfo
import io.hhplus.concert.domain.queue.QueueToken
import java.time.LocalDateTime

class QueueResponse {
    data class GetQueueTokenInfo(
        val id: String,
        val expiration: LocalDateTime,
        val status: String
    ) {
        companion object {
            fun fromEntity(queueToken: QueueToken): GetQueueTokenInfo {
                return GetQueueTokenInfo(
                    id = queueToken.id,
                    expiration = queueToken.expiration,
                    status = queueToken.status.name
                )
            }
        }
    }

    data class GetQueueTokenOrderInfo(
        val id: String,
        val expiration: LocalDateTime,
        val status: String,
        val order: Int
    ) {
        companion object {
            fun fromInfo(queueTokenOrderInfo: QueueTokenOrderInfo): GetQueueTokenOrderInfo {
                return GetQueueTokenOrderInfo(
                    id = queueTokenOrderInfo.id,
                    expiration = queueTokenOrderInfo.expiration,
                    status = queueTokenOrderInfo.status.name,
                    order = queueTokenOrderInfo.order
                )
            }
        }
    }
}