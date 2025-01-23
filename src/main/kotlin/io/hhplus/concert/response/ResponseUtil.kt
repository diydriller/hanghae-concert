package io.hhplus.concert.response

import jakarta.servlet.http.HttpServletResponse

object ResponseUtil {
    fun writeErrorResponse(
        response: HttpServletResponse,
        status: Int,
        message: String
    ) {
        response.status = status
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"
        response.writer.write("""
            {
                "isSuccess": false,
                "message": "$message"
            }
        """.trimIndent())
    }
}