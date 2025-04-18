package com.atlas.auth.services

import com.atlas.auth.persistence.entities.CredentialEntity
import com.atlas.auth.persistence.repositories.CredentialRepository
import com.atlas.common.models.auth.TokenResponse
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*

@Service
class CredentialService(
    private val credentialRepository: CredentialRepository,
    private val passwordEncoder: BCryptPasswordEncoder = BCryptPasswordEncoder(),
    @Value("\${jwt.secret}") private val jwtSecret: String,
    @Value("\${jwt.expiration}") private val jwtExpiration: Long
) {
    private val signingKey: Key by lazy {
        Keys.hmacShaKeyFor(jwtSecret.toByteArray())
    }

    fun registerCredential(userId: Long, email: String, password: String): Result<Unit> {
        return try {
            val existingCredential = credentialRepository.findByEmail(email)
            if (existingCredential != null) {
                return Result.failure(Exception("Credential with email $email already exists"))
            }
            val hashedPassword = passwordEncoder.encode(password)
            credentialRepository.save(
                CredentialEntity(
                    userId = userId,
                    email = email,
                    passwordHash = hashedPassword
                )
            )
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun validateCredential(email: String, password: String): Result<TokenResponse> {
        return try {
            val credential = credentialRepository.findByEmail(email)
            if (credential != null && passwordEncoder.matches(password, credential.passwordHash)) {
                val token = generateJwtToken(credential.userId)
                Result.success(TokenResponse(token = token))
            } else {
                Result.failure(Exception("Invalid credentials"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun generateJwtToken(userId: Long): String {
        return Jwts.builder()
            .setSubject(userId.toString())
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtExpiration))
            .signWith(signingKey, SignatureAlgorithm.HS512)
            .compact()
    }
}