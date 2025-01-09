package io.hhplus.concert.domain.concert

import com.github.f4b6a3.tsid.TsidCreator
import io.hhplus.concert.domain.BaseModel
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

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
}