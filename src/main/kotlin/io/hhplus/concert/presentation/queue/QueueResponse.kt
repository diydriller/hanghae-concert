package io.hhplus.concert.presentation.queue

import java.time.LocalDateTime
import java.util.*

class QueueResponse {
    data class QueueToken(
        val id: UUID,
        val expiration: LocalDateTime,
        val position: Int
    )
}