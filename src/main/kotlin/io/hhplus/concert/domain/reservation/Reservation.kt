package io.hhplus.concert.domain.reservation

import com.github.f4b6a3.tsid.TsidCreator
import io.hhplus.concert.domain.BaseModel
import jakarta.persistence.*

@Table(name = "reservation")
@Entity
class Reservation(
    @Id
    val id: String = TsidCreator.getTsid().toString(),
    val concertScheduleId: String,
    val seatId: String,
    val userId: String,
    val price: Int
) : BaseModel() {
    @Enumerated(EnumType.STRING)
    var status: Status = Status.PENDING

    fun reserve() {
        this.status = Status.RESERVED
    }

    fun isReserved(): Boolean {
        return this.status == Status.RESERVED
    }

    enum class Status {
        PENDING,
        RESERVED,
        COMPLETED
    }
}