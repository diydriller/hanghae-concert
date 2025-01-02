package io.hhplus.concert.presentation.payment

import io.hhplus.concert.response.BaseResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RequestMapping("/payment")
@RestController
class PaymentController {
    @PostMapping("")
    fun pay(payRequest: PaymentRequest.Pay): BaseResponse<PaymentResponse.Pay> {
        val response = PaymentResponse.Pay(
            1L,
            2000,
            LocalDateTime.now()
        )
        return BaseResponse(response)
    }
}