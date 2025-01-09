package io.hhplus.concert.presentation.payment


class PaymentRequest {
    data class Pay(
        val reservationId: Long
    )
}