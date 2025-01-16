package io.hhplus.concert.domain.concert

import com.github.f4b6a3.tsid.TsidCreator
import io.hhplus.concert.domain.BaseModel
import io.hhplus.concert.exception.ConflictException
import io.hhplus.concert.response.BaseResponseStatus
import jakarta.persistence.*
import java.time.LocalDateTime

@Table(name = "seat")
@Entity
class Seat(
    @Id
    val id: String = TsidCreator.getTsid().toString(),
    val number: Int,
    val price: Int,
    @ManyToOne
    @JoinColumn(name = "concert_schedule_id")
    val concertSchedule: ConcertSchedule
) : BaseModel() {
    @Enumerated(EnumType.STRING)
    var status = Status.IDLE

    var holdExpiration: LocalDateTime? = null

    fun isHolding(): Boolean {
        return status == Status.HOLD && (holdExpiration != null && holdExpiration!!.isAfter(LocalDateTime.now()))
    }

    fun hold() {
        if (isHolding()) {
            throw ConflictException(BaseResponseStatus.ALREADY_HOLDING_SEAT)
        }
        if (status == Status.RESERVED) {
            throw ConflictException(BaseResponseStatus.ALREADY_RESERVED)
        }
        this.status = Status.HOLD
        this.holdExpiration = LocalDateTime.now().plusMinutes(5)
    }

    fun expire() {
        this.status = Status.IDLE
    }

    enum class Status {
        HOLD, IDLE, RESERVED
    }
}