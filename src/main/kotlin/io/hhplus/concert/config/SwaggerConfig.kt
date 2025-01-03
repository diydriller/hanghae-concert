package io.hhplus.concert.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun chatOpenApi(): GroupedOpenApi {
        val paths = arrayOf("/**")

        return GroupedOpenApi.builder()
            .group("콘서트 예약 서비스")
            .pathsToMatch(*paths)
            .build()
    }

    @Bean
    fun openAPI(): OpenAPI {
        val info = Info()
            .version("v1")
            .title("콘서트 예약 서비스")
            .description("콘서튼 예약 서비스")

        return OpenAPI()
            .info(info)
    }
}