package io.hhplus.concert.application.queue

import io.hhplus.concert.domain.queue.QueueToken
import java.time.LocalDateTime

data class QueueTokenOrderInfo(
    val id: String,
    val expiration: LocalDateTime,
    val status: QueueToken.Status,
    val order: Int
)