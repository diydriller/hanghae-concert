package io.hhplus.concert.interceptor

import io.hhplus.concert.domain.queue.QueueReader
import io.hhplus.concert.response.BaseResponseStatus
import io.hhplus.concert.response.ResponseUtil
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class TokenValidationInterceptor(
    private val queueReader: QueueReader
) : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val tokenId = request.getHeader("tokenId")
            ?: run {
                ResponseUtil.writeErrorResponse(
                    response,
                    HttpServletResponse.SC_UNAUTHORIZED,
                    BaseResponseStatus.NOT_FOUND_TOKEN.message
                )
                return false
            }

        val token = queueReader.getQueueToken(tokenId)
            ?: run {
                ResponseUtil.writeErrorResponse(
                    response,
                    HttpServletResponse.SC_UNAUTHORIZED,
                    BaseResponseStatus.NOT_FOUND_TOKEN.message
                )
                return false
            }

        if (!token.isValid()) {
            ResponseUtil.writeErrorResponse(
                response,
                HttpServletResponse.SC_FORBIDDEN,
                BaseResponseStatus.NOT_VALID_TOKEN.message
            )
            return false
        }
        return true
    }
}