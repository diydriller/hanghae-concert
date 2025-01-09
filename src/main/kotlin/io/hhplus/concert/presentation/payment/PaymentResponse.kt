package io.hhplus.concert.presentation.payment

import java.time.LocalDateTime

class PaymentResponse {
    data class Pay(
        val id: Long,
        val amount: Int,
        val date: LocalDateTime
    )
}