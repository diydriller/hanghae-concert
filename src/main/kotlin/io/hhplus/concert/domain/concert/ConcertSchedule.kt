package io.hhplus.concert.domain.concert

import com.github.f4b6a3.tsid.TsidCreator
import io.hhplus.concert.domain.BaseModel
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

@Entity
class ConcertSchedule(
    @Id
    val id: String = TsidCreator.getTsid().toString(),
    @ManyToOne
    @JoinColumn(name = "concert_id")
    val concert: Concert,
    val date: LocalDateTime,
    val totalSeatCount: Int
) : BaseModel() {
    var reservedSeatCount: Int = 0
}