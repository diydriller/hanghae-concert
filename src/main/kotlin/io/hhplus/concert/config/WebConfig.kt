package io.hhplus.concert.config

import io.hhplus.concert.interceptor.TokenValidationInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val tokenValidationInterceptor: TokenValidationInterceptor
) : WebMvcConfigurer {
    private val excludedPaths = listOf(
        "/concert/**",
        "/queue/**"
    )

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(tokenValidationInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns(*excludedPaths.toTypedArray())
    }
}