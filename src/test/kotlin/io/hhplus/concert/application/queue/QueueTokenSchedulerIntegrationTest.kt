package io.hhplus.concert.application.queue

import com.github.f4b6a3.tsid.TsidCreator
import io.hhplus.concert.config.BaseIntegrationTest
import io.hhplus.concert.domain.queue.QueueToken
import io.hhplus.concert.infrastructure.queue.QueueTokenRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class QueueTokenSchedulerIntegrationTest : BaseIntegrationTest() {
    @Autowired
    private lateinit var queueTokenScheduler: QueueTokenScheduler

    @Autowired
    private lateinit var queueTokenRepository: QueueTokenRepository

    @Test
    fun `토큰 활성화 함수 호출시 10개씩 활성화된다`() {
        // given
        val queueTokenIdList: MutableList<String> = mutableListOf()
        repeat(30) {
            val queueToken = QueueToken(
                userId = TsidCreator.getTsid().toString(),
                expiration = LocalDateTime.now().plusMinutes(10)
            )
            val savedToken = queueTokenRepository.save(queueToken)
            queueTokenIdList.add(savedToken.id)
        }

        // when
        queueTokenScheduler.activateQueueToken()

        // then
        var activeTokenCount = 0
        queueTokenIdList.forEach { queueTokenId ->
            val savedToken = queueTokenRepository.findQueueTokenById(queueTokenId)
            if (savedToken?.status == QueueToken.Status.ACTIVE) {
                activeTokenCount++
            }
        }
        assertEquals(10, activeTokenCount)
    }
}