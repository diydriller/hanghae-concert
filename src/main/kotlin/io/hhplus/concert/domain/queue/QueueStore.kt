package io.hhplus.concert.domain.queue

interface QueueStore {
    fun saveQueueToken(queueToken: QueueToken): QueueToken
}