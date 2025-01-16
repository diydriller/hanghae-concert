package io.hhplus.concert.filter

import com.github.f4b6a3.tsid.TsidCreator
import jakarta.servlet.*
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.stereotype.Component

@Component
@WebFilter(urlPatterns = ["/*"])
class LoggingFilter : Filter {
    private val logger = LoggerFactory.getLogger(LoggingFilter::class.java)

    override fun init(filterConfig: FilterConfig?) {
    }

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpRequest = request as HttpServletRequest
        val httpResponse = response as HttpServletResponse

        try {
            MDC.put("requestId", TsidCreator.getTsid().toString())
            MDC.put("uri", httpRequest.requestURI)
            MDC.put("method", httpRequest.method)
            MDC.put("queryString", httpRequest.queryString ?: "")
            MDC.put("remoteAddr", httpRequest.remoteAddr)

            logRequest(httpRequest)

            chain.doFilter(request, response)

            logResponse(httpResponse)
        } finally {
            MDC.clear()
        }
    }

    override fun destroy() {
    }

    private fun logRequest(request: HttpServletRequest) {
        val headers = request.headerNames.asSequence().joinToString(", ") { headerName ->
            "$headerName: ${request.getHeader(headerName)}"
        }

        logger.info("Request Log: [Headers: $headers]")
    }

    private fun logResponse(response: HttpServletResponse) {
        val status = response.status

        logger.info("Response Log: [Status: $status]")
    }
}