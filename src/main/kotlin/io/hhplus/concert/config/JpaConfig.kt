package io.hhplus.concert.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableJpaAuditing
@EnableTransactionManagement
@EntityScan(basePackages = ["io.hhplus.concert.domain"])
@EnableJpaRepositories(basePackages = ["io.hhplus.concert.infrastructure"])
class JpaConfig