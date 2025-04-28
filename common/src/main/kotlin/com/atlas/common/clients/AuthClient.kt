package com.atlas.common.clients

import com.atlas.common.dtos.auth.RegisterCredentialRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class AuthClient(
    private val webClientBuilder: WebClient.Builder,
    @Value("\${services.auth.baseurl}") private val authServiceUrl: String
) {
    fun registerCredential(userId: Long, email: String, password: String): Result<Unit> {
        val request = RegisterCredentialRequest(userId, email, password)
        return webClientBuilder.build()
            .post()
            .uri("$authServiceUrl/register")
            .bodyValue(request)
            .retrieve()
            .toBodilessEntity()
            .map { response ->
                if (response.statusCode.is2xxSuccessful) {
                    Result.success(Unit)
                } else {
                    Result.failure(Exception("Failed to register credential: ${response.statusCode}"))
                }
            }
            .block() ?: Result.failure(Exception("Failed to register credential: No response"))
    }
}