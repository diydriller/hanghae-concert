package io.hhplus.concert.domain.point

import com.github.f4b6a3.tsid.TsidCreator
import io.hhplus.concert.domain.BaseModel
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Table(name = "user_point")
@Entity
class UserPoint(
    @Id
    val id: String = TsidCreator.getTsid().toString(),
    val userId: String
) : BaseModel() {
    var point: Int = 0
}