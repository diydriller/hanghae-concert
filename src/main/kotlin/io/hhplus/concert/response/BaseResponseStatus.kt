package io.hhplus.concert.response

import org.springframework.http.HttpStatus

enum class BaseResponseStatus(
    val isSuccess: Boolean,
    val status: String,
    val message: String
) {
    SUCCESS(true, HttpStatus.OK.name, "요청에 성공하였습니다.")
}