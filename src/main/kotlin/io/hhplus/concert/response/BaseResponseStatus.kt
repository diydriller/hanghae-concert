package io.hhplus.concert.response

enum class BaseResponseStatus(
    val isSuccess: Boolean,
    val message: String
) {
    SUCCESS(true, "요청에 성공하였습니다."),
    NOT_FOUND_USER(false, "유저를 찾을 수 없습니다."),
    NOT_FOUND_TOKEN(false, "토큰을 찾을 수 없습니다.")
}