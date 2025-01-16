package io.hhplus.concert.infrastructure.payment

import io.hhplus.concert.domain.payment.Payment
import io.hhplus.concert.domain.payment.PaymentStore
import org.springframework.stereotype.Component

@Component
class PaymentStoreImpl(
    private val paymentRepository: PaymentRepository
) : PaymentStore {
    override fun savePayment(payment: Payment): Payment {
        return paymentRepository.save(payment)
    }
}