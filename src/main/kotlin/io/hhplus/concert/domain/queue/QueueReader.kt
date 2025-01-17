package io.hhplus.concert.domain.queue

interface QueueReader {
    fun getQueueToken(tokenId: String): QueueToken?

    fun getQueueTokenOrder(tokenId: String): Int

    fun getInActiveQueueTokenList(num: Int): List<QueueToken>
}