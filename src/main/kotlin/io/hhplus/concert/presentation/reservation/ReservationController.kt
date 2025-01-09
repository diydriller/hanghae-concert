package io.hhplus.concert.presentation.reservation

import io.hhplus.concert.response.BaseResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RequestMapping("/reservation")
@RestController
class ReservationController {
    @PostMapping
    fun reserveConcert(
        @RequestBody request: ReservationRequest.ReserveConcert
    ): BaseResponse<ReservationResponse.ReserveConcert> {
        val response = ReservationResponse.ReserveConcert(
            1L,
            LocalDateTime.now().plusMinutes(5)
        )
        return BaseResponse(response)
    }
}