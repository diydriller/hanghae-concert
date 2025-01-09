package io.hhplus.concert.exception

import io.hhplus.concert.response.BaseResponseStatus

class ConflictException(val conflictStatus: BaseResponseStatus) : BaseException(conflictStatus)