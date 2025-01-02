package io.hhplus.concert.response

data class BaseResponse<T>(
    val isSuccess: Boolean,
    val message: String,
    val data: T? = null
) {
    constructor(data: T) : this(
        isSuccess = BaseResponseStatus.SUCCESS.isSuccess,
        message = BaseResponseStatus.SUCCESS.message,
        data = data
    )

    constructor(status: BaseResponseStatus) : this(
        isSuccess = status.isSuccess,
        message = status.message
    )
}