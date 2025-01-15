package io.hhplus.concert.domain.concert

import com.github.f4b6a3.tsid.TsidCreator
import io.hhplus.concert.domain.BaseModel
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Table(name = "concert")
@Entity
class Concert(
    @Id
    val id: String = TsidCreator.getTsid().toString(),
    var name: String
) : BaseModel() {
}