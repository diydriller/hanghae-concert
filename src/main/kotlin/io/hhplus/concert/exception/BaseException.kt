package io.hhplus.concert.exception

import io.hhplus.concert.response.BaseResponseStatus

open class BaseException(val status: BaseResponseStatus) : RuntimeException(status.message)
