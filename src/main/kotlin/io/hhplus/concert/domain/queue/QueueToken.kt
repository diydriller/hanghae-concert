package io.hhplus.concert.domain.queue

import com.github.f4b6a3.tsid.TsidCreator
import io.hhplus.concert.domain.BaseModel
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import java.time.LocalDateTime


@Entity
class QueueToken(
    @Id
    val id: String = TsidCreator.getTsid().toString(),
    val userId: String,
    val expiration: LocalDateTime,
) : BaseModel() {
    @Enumerated(EnumType.STRING)
    var status: Status = Status.INACTIVE

    enum class Status {
        ACTIVE, INACTIVE
    }
}