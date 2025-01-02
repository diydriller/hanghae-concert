package io.hhplus.concert.domain.user

import com.github.f4b6a3.tsid.TsidCreator
import io.hhplus.concert.domain.BaseModel
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class User(
    @Id
    val id: String = TsidCreator.getTsid().toString(),
    var name: String,
    val email: String,
    var password: String
) : BaseModel() {
}