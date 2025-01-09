package io.hhplus.concert.presentation.point

import io.hhplus.concert.response.BaseResponse
import org.springframework.web.bind.annotation.*

@RequestMapping("user")
@RestController
class PointController {
    @GetMapping("/point")
    fun getPoint(): BaseResponse<PointResponse.GetPoint> {
        val response = PointResponse.GetPoint(
            200000
        )
        return BaseResponse(response)
    }

    @PostMapping("/point")
    fun chargePoint(
        @RequestBody request: PointRequest.ChargePoint
    ): BaseResponse<PointResponse.GetPoint> {
        val response = PointResponse.GetPoint(
            500000
        )
        return BaseResponse(response)
    }
}