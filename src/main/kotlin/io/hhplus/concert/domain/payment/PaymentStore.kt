package io.hhplus.concert.domain.payment

interface PaymentStore {
    fun savePayment(payment: Payment): Payment
}