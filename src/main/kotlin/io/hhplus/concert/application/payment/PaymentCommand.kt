package io.hhplus.concert.application.payment

data class PaymentCommand(
    val userId: String,
    val reservationId: String
)