package io.hhplus.concert.config

import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
abstract class BaseIntegrationTest {
    companion object {
        @Container
        private val mysqlContainer = MySQLContainer<Nothing>("mysql:8.0").apply {
            withDatabaseName("testdb")
            withUsername("root")
            withPassword("rootpass")
        }

        @JvmStatic
        @DynamicPropertySource
        fun setDatasourceProperties(registry: DynamicPropertyRegistry) {
            registry.add("storage.datasource.core.jdbc-url") { mysqlContainer.jdbcUrl }
            registry.add("storage.datasource.core.username") { mysqlContainer.username }
            registry.add("storage.datasource.core.password") { mysqlContainer.password }
        }
    }
}