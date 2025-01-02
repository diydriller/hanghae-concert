package io.hhplus.concert.domain.payment

import com.github.f4b6a3.tsid.TsidCreator
import io.hhplus.concert.domain.BaseModel
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Payment(
    @Id
    val id: String = TsidCreator.getTsid().toString(),
    val userId: String,
    val reservationId: String,
    val totalPrice: Int
) : BaseModel() {
}