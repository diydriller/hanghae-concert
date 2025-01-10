package io.hhplus.concert.application.queue

import com.github.f4b6a3.tsid.TsidCreator
import io.hhplus.concert.config.BaseIntegrationTest
import io.hhplus.concert.domain.user.User
import io.hhplus.concert.infrastructure.user.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class QueueServiceIntegrationTest : BaseIntegrationTest() {
    @Autowired
    private lateinit var queueService: QueueService

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun `토큰 생성시 아이디는 순서대로 생성된다`() {
        // given
        val userId1 = TsidCreator.getTsid().toString()
        val userId2 = TsidCreator.getTsid().toString()
        val user1 = User(
            id = userId1,
            name = "aaa",
            email = "aaa@naver.com",
            password = "aaa"
        )
        val user2 = User(
            id = userId2,
            name = "bbb",
            email = "bbb@naver.com",
            password = "bbb"
        )
        userRepository.save(user1)
        userRepository.save(user2)

        // when
        val token1 = queueService.publishQueueToken(userId1)
        val token2 = queueService.publishQueueToken(userId2)

        // then
        assertThat(token1.id).isLessThan(token2.id)
    }
}