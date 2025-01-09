package io.hhplus.concert.exception

import io.hhplus.concert.response.BaseResponseStatus

class NotFoundException(val notFoundStatus: BaseResponseStatus) : BaseException(notFoundStatus)