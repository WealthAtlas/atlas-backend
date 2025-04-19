package com.atlas.app

import com.atlas.common.BaseIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort

class HealthCheckIntegrationTest : BaseIntegrationTest() {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    fun `health endpoint should return UP`() {
        val response = restTemplate.getForEntity("http://localhost:$port/actuator/health", String::class.java)
        assertThat(response.statusCode.is2xxSuccessful).isTrue()
        assertThat(response.body).contains("\"status\":\"UP\"")
    }
}