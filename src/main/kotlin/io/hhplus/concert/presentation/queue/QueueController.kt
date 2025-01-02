package io.hhplus.concert.presentation.queue

import io.hhplus.concert.response.BaseResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.util.*

@RequestMapping("/queue")
@RestController
class QueueController {
    @PostMapping("/create")
    fun publishQueueToken(): BaseResponse<QueueResponse.QueueToken> {
        val response = QueueResponse.QueueToken(
            UUID.randomUUID(),
            LocalDateTime.now().plusMinutes(5),
            1
        )
        return BaseResponse(response)
    }

    @GetMapping("/status")
    fun getWaitingInformation(): BaseResponse<QueueResponse.QueueToken> {
        val response = QueueResponse.QueueToken(
            UUID.randomUUID(),
            LocalDateTime.now().plusMinutes(5),
            1
        )
        return BaseResponse(response)
    }
}