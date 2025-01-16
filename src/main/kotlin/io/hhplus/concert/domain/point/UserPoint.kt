package io.hhplus.concert.domain.point

import com.github.f4b6a3.tsid.TsidCreator
import io.hhplus.concert.domain.BaseModel
import io.hhplus.concert.exception.ConflictException
import io.hhplus.concert.response.BaseResponseStatus
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

    fun charge(point: Int) {
        this.point += point
    }

    fun spend(point: Int) {
        if (this.point < point) throw ConflictException(BaseResponseStatus.NOT_ENOUGH_POINT)
        this.point -= point
    }
}