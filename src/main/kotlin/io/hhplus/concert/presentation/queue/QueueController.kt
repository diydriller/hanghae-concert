package io.hhplus.concert.presentation.queue

import io.hhplus.concert.application.queue.QueueService
import io.hhplus.concert.response.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/queue")
@RestController
class QueueController(
    val queueService: QueueService
) {
    @PostMapping("/create")
    fun publishQueueToken(
        @RequestHeader userId: String
    ): ResponseEntity<BaseResponse<QueueResponse.GetQueueTokenInfo>> {
        val queueToken = queueService.publishQueueToken(userId)
        val response = QueueResponse.GetQueueTokenInfo.fromEntity(queueToken)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            BaseResponse(response)
        )
    }

    @GetMapping("/status")
    fun getQueueToken(
        @RequestHeader tokenId: String
    ): ResponseEntity<BaseResponse<QueueResponse.GetQueueTokenOrderInfo>> {
        val queueTokenOrderInfo = queueService.getQueueToken(tokenId)
        val response = QueueResponse.GetQueueTokenOrderInfo.fromInfo(queueTokenOrderInfo)
        return ResponseEntity.ok().body(
            BaseResponse(response)
        )
    }
}