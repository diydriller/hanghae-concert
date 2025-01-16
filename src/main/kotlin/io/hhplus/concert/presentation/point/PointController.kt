package io.hhplus.concert.presentation.point

import io.hhplus.concert.application.point.PointCommand
import io.hhplus.concert.application.point.PointService
import io.hhplus.concert.response.BaseResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/point")
@RestController
class PointController(
    private val pointService: PointService
) {
    @GetMapping
    fun getPoint(
        @RequestHeader userId: String
    ): ResponseEntity<BaseResponse<PointResponse.GetPoint>> {
        val userPoint = pointService.getPoint(PointCommand.GetPoint(userId))
        return ResponseEntity.ok().body(
            BaseResponse(PointResponse.GetPoint(userPoint.point))
        )
    }

    @PutMapping("/charge")
    fun chargePoint(
        @RequestHeader userId: String,
        @RequestBody request: PointRequest.ChargePoint
    ): ResponseEntity<BaseResponse<PointResponse.GetPoint>> {
        val userPoint = pointService.chargePoint(PointCommand.ChargePoint(userId, request.point))
        return ResponseEntity.ok().body(
            BaseResponse(PointResponse.GetPoint(userPoint.point))
        )
    }
}