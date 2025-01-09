package io.hhplus.concert.domain.reservation

import com.github.f4b6a3.tsid.TsidCreator
import io.hhplus.concert.domain.BaseModel
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id

@Entity
class Reservation(
    @Id
    val id: String = TsidCreator.getTsid().toString(),
    val concertScheduleId: String,
    val seatId: String,
    val userId: String
) : BaseModel() {
    @Enumerated(EnumType.STRING)
    var status: Status = Status.PENDING

    enum class Status {
        PENDING,
        RESERVED,
        COMPLETED,
        EXPIRED,
        CANCELED
    }
}